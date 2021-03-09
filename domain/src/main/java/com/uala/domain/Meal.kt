package com.uala.domain


data class DataMeal(
    val name: List<Meal>? = emptyList()
)

data class Meal(
    val id: Long = 0,
    val name: String,
    val image: String,
    val instructions: String = "",
    val category: String,
    val url: String = ""
)