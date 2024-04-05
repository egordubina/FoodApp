package ru.egordubina.foodapp.data.api

import ru.egordubina.foodapp.domain.models.Category

interface CategoryApi {
    suspend fun loadALlCategories(): List<Category>
}