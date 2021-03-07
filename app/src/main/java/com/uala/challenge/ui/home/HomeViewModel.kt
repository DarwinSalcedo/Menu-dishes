package com.uala.challenge.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uala.challenge.network.Meal
import com.uala.challenge.network.MealsApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

enum class ApiStatus { LOADING, ERROR, DONE }

class HomeViewModel : ViewModel() {


    private var bannerActive: Boolean = true

    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<Meal>>()

    val properties: LiveData<List<Meal>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<Meal>()

    val navigateToSelectedProperty: LiveData<Meal>
        get() = _navigateToSelectedProperty


    private val _statusBanner = MutableLiveData<ApiStatus>()
    val statusBanner: LiveData<ApiStatus>
        get() = _statusBanner

    private val _bannerPlate = MutableLiveData<Meal>()
    val bannerPlate: LiveData<Meal>
        get() = _bannerPlate

    init {
        getListMeals("a")
        getRandomPlate()
    }

    fun getListMeals(filter: String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val result = MealsApi.RETROFIT_SERVICE.getMeals(filter)
                if (result.name != null) {
                    _properties.value = result.name!!
                    _status.value = ApiStatus.DONE
                    Timber.e(" result " + result)
                }

            } catch (e: Exception) {
                Timber.e(" result " + e)
                _status.value = ApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun getRandomPlate() {
        viewModelScope.launch {
           while (bannerActive){
               _statusBanner.value = ApiStatus.LOADING
               try {
                   val result = MealsApi.RETROFIT_SERVICE.getDetailMeals()
                   if (result.name != null) {
                       _bannerPlate.value = result.name.first()
                       _statusBanner.value = ApiStatus.DONE
                       Timber.e(" result " + result)
                   }

               } catch (e: Exception) {
                   Timber.e(" result " + e)
                   _statusBanner.value = ApiStatus.ERROR
               }
               delay(30_000)
           }

        }
    }


    fun displayPropertyDetails(Meal: Meal) {
        _navigateToSelectedProperty.value = Meal
    }


    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        bannerActive = false
    }
}