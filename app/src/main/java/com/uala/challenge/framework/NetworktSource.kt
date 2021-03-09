package com.uala.challenge.framework

import android.os.Parcelable
import com.squareup.moshi.Json
import com.uala.data.MealPersistenceSource
import com.uala.domain.DataMeal
import com.uala.domain.Meal
import kotlinx.android.parcel.Parcelize

class NetworktSource : MealPersistenceSource {

    override suspend fun getListMeals(filter: String): DataMeal {
        val result = MealsApi.RETROFIT_SERVICE.getMeals(filter)
        return result.toDataMeal()
    }

    override suspend fun getRandomMeal(): DataMeal {
        val result = MealsApi.RETROFIT_SERVICE.getRandomMeal()
        return result.toDataMeal()
    }

    override suspend fun getDetailsMeal(id: Long): DataMeal {
        val result = MealsApi.RETROFIT_SERVICE.getDetailMeals(id)
        return result.toDataMeal()
    }

}


@Parcelize
data class NDataMeal(
    @Json(name = "meals") val name: List<NMeal>? = emptyList()
) : Parcelable

@Parcelize
data class NMeal(
    @Json(name = "idMeal") val id: Long = 0,
    @Json(name = "strMeal") val name: String,
    @Json(name = "strMealThumb") val image: String,
    @Json(name = "strInstructions") val instructions: String = "",
    @Json(name = "strCategory") val category: String,
    @Json(name = "strYoutube") val url: String = ""
) : Parcelable


fun NDataMeal.toDataMeal(): DataMeal {
    val data = this.name?.map { item ->
        Meal(
            item.id,
            item.name,
            item.image,
            item.instructions,
            item.category,
            item.url
        )
    }
    println(data)
    return DataMeal(data)

}


fun NMeal.toDataMeal(): Meal {
    return Meal(
        this.id,
        this.name,
        this.image,
        this.instructions,
        this.category,
        this.url
    )
}

fun Meal.toNDataMeal(): NMeal {
    return NMeal(
        this.id,
        this.name,
        this.image,
        this.instructions,
        this.category,
        this.url
    )
}

