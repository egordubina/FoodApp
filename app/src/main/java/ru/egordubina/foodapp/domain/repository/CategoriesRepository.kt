package ru.egordubina.foodapp.domain.repository

import ru.egordubina.foodapp.domain.models.Category

interface CategoriesRepository {
    suspend fun loadCategories(): List<Category>
}