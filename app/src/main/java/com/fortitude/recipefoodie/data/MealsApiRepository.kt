package com.fortitude.recipefoodie.data

import com.fortitude.recipefoodie.models.Meals
import com.fortitude.recipefoodie.models.MealsDetails
import javax.inject.Inject

class MealsApiRepository @Inject constructor(private val api: MealsRecipeApi)  {

    suspend fun getMealsByCategoryName(categoryName: String): Meals? {
        return try {
            api.getMealsByCategory(categoryName)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getMealsDetailsById(id: String): MealsDetails? {
        return try {
            api.getMealById(id)
        } catch (e: Exception) {
            null
        }
    }
}