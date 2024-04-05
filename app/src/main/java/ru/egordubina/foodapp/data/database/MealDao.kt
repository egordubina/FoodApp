package ru.egordubina.foodapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.egordubina.foodapp.data.models.MealDBO

@Dao
interface MealDao {
    @Query("SELECT * FROM mealdbo")
    fun getAll(): List<MealDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mealDBO: MealDBO)

    @Query("DELETE FROM mealdbo")
    fun deleteAll()
}