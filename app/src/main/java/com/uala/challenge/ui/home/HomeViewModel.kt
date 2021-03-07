package com.uala.challenge.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uala.challenge.network.Meal
import com.uala.challenge.network.MealsApi
import kotlinx.coroutines.launch
import timber.log.Timber

enum class ApiStatus { LOADING, ERROR, DONE }

class HomeViewModel : ViewModel() {


    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of Meal
    // with new values
    private val _properties = MutableLiveData<List<Meal>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<Meal>>
        get() = _properties

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<Meal>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<Meal>
        get() = _navigateToSelectedProperty



    init {
        getListMeals("a")
    }

     fun getListMeals(filter: String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
              val result = MealsApi.RETROFIT_SERVICE.getMeals(filter)
                if(result.name!=null) {
                    _properties.value = result.name!!
                        _status.value = ApiStatus.DONE
                    Timber.e(" result "+result)
                }

            } catch (e: Exception) {
                Timber.e(" result "+e)
                _status.value = ApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    /**
     */

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param Meal The [Meal] that was clicked on.
     */
    fun displayPropertyDetails(Meal: Meal) {
        _navigateToSelectedProperty.value = Meal
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = Meal("","","")
    }


    fun updateFilter(filter: String) {
        getListMeals(filter)
    }
}