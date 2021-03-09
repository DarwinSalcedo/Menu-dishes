package com.uala.challenge.usecase

import com.uala.challenge.data.MealsRepository
import com.uala.challenge.domain.DataMeal

class GetListMeals(private val mealsRepository: MealsRepository) {

    suspend operator fun invoke(value: String): DataMeal = mealsRepository.getMeals(filter = value)

}
