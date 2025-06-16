package com.fortitude.recipefoodie.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fortitude.recipefoodie.models.MealItem

@Database(
    entities = [MealItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dao(): AppDao
}