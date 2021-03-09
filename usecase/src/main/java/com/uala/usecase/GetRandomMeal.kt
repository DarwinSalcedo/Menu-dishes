package com.uala.usecase

import com.uala.data.MealsRepository
import com.uala.domain.DataMeal

class GetRandomMeal(private val mealsRepository: MealsRepository) {

    suspend operator fun invoke(): DataMeal = mealsRepository.getRandomMeal()

}
