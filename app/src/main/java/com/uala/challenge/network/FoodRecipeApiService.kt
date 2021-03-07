
package com.uala.challenge.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.uala.challenge.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BuildConfig.URL)
        .build()

/**
 * A public interface that exposes the [getMeals] method
 */
interface MealsApiService {

    @GET("search.php")
    suspend fun getMeals(@Query("s") type: String): ListMeal

    @GET("lookup.php")
    suspend fun getDetailMeals(@Query("i") id: Long): ListMeal

    @GET("random.php")
    suspend fun getDetailMeals(): ListMeal
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MealsApi {
    val RETROFIT_SERVICE : MealsApiService by lazy { retrofit.create(MealsApiService::class.java) }
}
