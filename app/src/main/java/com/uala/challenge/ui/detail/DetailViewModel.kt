/*
 *  Copyright 2018, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.uala.challenge.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uala.challenge.network.Meal
import com.uala.challenge.network.MealsApi
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(meal: Meal, app: Application) : AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<Meal>()

    val selectedProperty: LiveData<Meal>
        get() = _selectedProperty

    init {
        _selectedProperty.value = meal
        meal.id?.let { getInstructionsMeals(it) }
    }

    private val _displayInstructions = MutableLiveData<String>()

    val displayInstructions: LiveData<String>
        get() = _displayInstructions


    private fun getInstructionsMeals(id: Long) {
        viewModelScope.launch {
            try {
                val result = MealsApi.RETROFIT_SERVICE.getDetailMeals(id)
                var instrucctions = ""
                result.name?.forEach { instrucctions += it.instructions + "\n" }
                _displayInstructions.postValue(instrucctions)

            } catch (e: Exception) {
                Timber.e(" result " + e)
            }
        }
    }
}
