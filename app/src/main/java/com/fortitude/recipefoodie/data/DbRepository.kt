package com.fortitude.recipefoodie.data

import com.fortitude.recipefoodie.db.AppDao
import com.fortitude.recipefoodie.models.MealItem
import javax.inject.Inject

class DbRepository @Inject constructor(val dao: AppDao) {
    suspend fun getOwnMeals() = dao.getOwnMeals()
    suspend fun insertOwnMeal(meal: MealItem) = dao.insertOwnMeal(meal)
    suspend fun getOwnMealById(id: Int) = dao.getOwnMealById(id)
}