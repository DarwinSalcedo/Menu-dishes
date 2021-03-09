package com.uala.data

import com.uala.domain.DataMeal


class MealsRepository(
    private val dataSource: MealPersistenceSource
) {

    suspend fun getMeals(filter: String): DataMeal = dataSource.getListMeals(filter = filter)

    suspend fun getRandomMeal(): DataMeal = dataSource.getRandomMeal()

    suspend fun getDetailsMeal(id: Long): DataMeal = dataSource.getDetailsMeal(id = id)

}

interface MealPersistenceSource {

   suspend fun getListMeals(filter : String): DataMeal

   suspend fun getRandomMeal(): DataMeal

   suspend fun getDetailsMeal(id : Long): DataMeal

}
