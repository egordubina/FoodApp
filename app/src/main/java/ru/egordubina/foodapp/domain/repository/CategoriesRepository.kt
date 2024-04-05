package ru.egordubina.foodapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.egordubina.foodapp.domain.models.Category

interface CategoriesRepository {
    val categories: Flow<List<Category>>
}