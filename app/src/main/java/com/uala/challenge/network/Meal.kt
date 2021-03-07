package com.uala.challenge.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ListMeal(
    @Json(name = "meals") val name: List<Meal>?)  : Parcelable

@Parcelize
data class Meal(
    @Json(name = "strMeal") val name: String,
    @Json(name = "strMealThumb") val imgSrcUrl: String,
    val strCategory: String
) : Parcelable