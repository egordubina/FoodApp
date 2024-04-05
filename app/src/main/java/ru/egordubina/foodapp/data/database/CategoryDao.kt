package ru.egordubina.foodapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.egordubina.foodapp.data.models.CategoryDBO

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categorydbo")
    fun getAll(): List<CategoryDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categoryDBO: CategoryDBO)

    @Query("DELETE FROM categorydbo")
    fun deleteAll()
}