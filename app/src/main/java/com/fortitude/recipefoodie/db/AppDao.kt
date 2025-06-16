package com.fortitude.recipefoodie.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fortitude.recipefoodie.models.MealItem

@Dao
interface AppDao {
    @Query("SELECT * FROM meals")
    suspend fun getOwnMeals(): List<MealItem>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwnMeal(meal: MealItem)

    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getOwnMealById(id: Int): MealItem?

}