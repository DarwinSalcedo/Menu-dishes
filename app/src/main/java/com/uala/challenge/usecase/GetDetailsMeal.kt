package com.uala.challenge.usecase

import com.uala.challenge.data.MealsRepository
import com.uala.challenge.domain.DataMeal

class GetDetailsMeal(private val mealsRepository: MealsRepository) {

    suspend operator fun invoke(id: Long): DataMeal = mealsRepository.getDetailsMeal(id = id)

}
