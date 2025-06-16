package com.fortitude.recipefoodie.data

import com.fortitude.recipefoodie.models.Meals
import com.fortitude.recipefoodie.models.MealsDetails
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MealsRecipeApi {
    //www.themealdb.com/api/json/v1/1/filter.php?c=Miscellaneous
    /**
     * Get list of recipes by category
     *
     * @param categoryName recipes category name
     */

    @GET("filter.php?")
    suspend fun getMealsByCategory(
        @Query("c") categoryName: String
    ): Meals?

    //www.themealdb.com/api/json/v1/1/lookup.php?i=52848
    /**
     * Get recipe details by id
     *
     * @param mealId id of the recipe
     */

    @GET("lookup.php?")
    suspend fun getMealById(
        @Query("i") mealId: String
    ): MealsDetails?
}