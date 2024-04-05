package ru.egordubina.foodapp.ui.models

import ru.egordubina.foodapp.domain.models.Category

data class CategoryUi(
    val id: Int,
    val categoryName: String,
    val thumbnail: String,
    val description: String,
)

fun Category.asUi(): CategoryUi = CategoryUi(
    id = id,
    categoryName = categoryName,
    thumbnail = thumbnail,
    description = description
)