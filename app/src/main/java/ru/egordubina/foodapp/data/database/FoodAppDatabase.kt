package ru.egordubina.foodapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.egordubina.foodapp.data.models.CategoryDBO
import ru.egordubina.foodapp.data.models.MealDBO

@Database(entities = [CategoryDBO::class, MealDBO::class], version = 1)
abstract class FoodAppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun mealDao(): MealDao
}