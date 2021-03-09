package com.uala.challenge.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uala.domain.Meal
import com.uala.usecase.GetListMeals
import com.uala.usecase.GetRandomMeal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

enum class ApiStatus { LOADING, ERROR, DONE }

class HomeViewModel(
    private val getMeals: GetListMeals,
    private val getRandomMeal: GetRandomMeal,
    application: Application
) :
    AndroidViewModel(application) {


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
        getListMeals("")
        executeRandomMeal()
    }

    fun getListMeals(filter: String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val result = getMeals(filter)
                _status.value = ApiStatus.DONE
                Timber.e(" result %s", result)

                if (!result.name.isNullOrEmpty()) _properties.value = result.name

            } catch (e: Exception) {
                Timber.e(e)
                _status.value = ApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    private fun executeRandomMeal() {
        viewModelScope.launch {
            while (bannerActive) {
                _statusBanner.value = ApiStatus.LOADING
                try {
                    val result = getRandomMeal()
                    _statusBanner.value = ApiStatus.DONE
                    Timber.e(" result %s", result)

                    if (!result.name.isNullOrEmpty()) _bannerPlate.value = result.name?.first()


                } catch (e: Exception) {
                    Timber.e(e)
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