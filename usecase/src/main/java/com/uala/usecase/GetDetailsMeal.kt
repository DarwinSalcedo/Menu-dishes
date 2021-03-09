package com.uala.usecase

import com.uala.data.MealsRepository
import com.uala.domain.DataMeal

class GetDetailsMeal(private val mealsRepository: MealsRepository) {

    suspend operator fun invoke(id: Long): DataMeal = mealsRepository.getDetailsMeal(id = id)

}
