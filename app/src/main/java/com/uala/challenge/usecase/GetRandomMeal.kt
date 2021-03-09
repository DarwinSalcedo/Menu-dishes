package com.uala.challenge.usecase

import com.uala.challenge.data.MealsRepository
import com.uala.challenge.domain.DataMeal

class GetRandomMeal(private val mealsRepository: MealsRepository) {

    suspend operator fun invoke(): DataMeal = mealsRepository.getRandomMeal()

}
