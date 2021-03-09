package com.uala.challenge.framework

import com.uala.challenge.data.MealPersistenceSource
import com.uala.challenge.domain.DataMeal

class NetworktSource : MealPersistenceSource {

    override suspend fun getListMeals(filter: String): DataMeal {
        return MealsApi.RETROFIT_SERVICE.getMeals(filter)
    }

    override suspend fun getRandomMeal(): DataMeal {
        return MealsApi.RETROFIT_SERVICE.getRandomMeal()
    }

    override suspend fun getDetailsMeal(id: Long): DataMeal {
        return MealsApi.RETROFIT_SERVICE.getDetailMeals(id)
    }

}