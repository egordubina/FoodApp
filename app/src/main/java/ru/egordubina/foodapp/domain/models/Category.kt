package ru.egordubina.foodapp.domain.models

data class Category(
    val id: Int,
    val categoryName: String,
    val thumbnail: String,
    val description: String,
)