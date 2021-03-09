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

package com.uala.challenge.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uala.challenge.usecase.GetDetailsMeal
import com.uala.challenge.usecase.GetListMeals
import com.uala.challenge.usecase.GetRandomMeal

class HomeViewModelFactory(
    private val listMeals: GetListMeals,
    private val randomMeal: GetRandomMeal,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(listMeals,randomMeal, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
