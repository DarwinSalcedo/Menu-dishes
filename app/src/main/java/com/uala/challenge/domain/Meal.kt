package com.uala.challenge.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DataMeal(
    @Json(name = "meals") val name: List<Meal>? = emptyList())  : Parcelable

@Parcelize
data class Meal(
    @Json(name = "idMeal") val id: Long = 0,
    @Json(name = "strMeal") val name: String,
    @Json(name = "strMealThumb") val image: String,
    @Json(name = "strInstructions") val instructions: String = "",
    @Json(name = "strCategory") val category: String,
    @Json(name = "strYoutube") val url: String = ""
) : Parcelable