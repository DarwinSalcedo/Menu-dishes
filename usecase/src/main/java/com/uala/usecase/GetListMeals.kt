package com.uala.usecase

import com.uala.data.MealsRepository
import com.uala.domain.DataMeal

class GetListMeals(private val mealsRepository: MealsRepository) {

    suspend operator fun invoke(value: String): DataMeal = mealsRepository.getMeals(filter = value)

}
