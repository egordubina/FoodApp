package ru.egordubina.foodapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryDBO(
    @ColumnInfo("idCategory") @PrimaryKey val id: String,
    @ColumnInfo("strCategory") val categoryName: String,
    @ColumnInfo("strCategoryThumb") val thumbnail: String,
    @ColumnInfo("strCategoryDescription") val description: String,
)
