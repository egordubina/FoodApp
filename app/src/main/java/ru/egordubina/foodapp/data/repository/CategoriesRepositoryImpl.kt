package ru.egordubina.foodapp.data.repository

import ru.egordubina.foodapp.data.api.CategoryApi
import ru.egordubina.foodapp.domain.models.Category
import ru.egordubina.foodapp.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoryApi: CategoryApi,
) : CategoriesRepository {
    override suspend fun loadCategories(): List<Category> = categoryApi.loadALlCategories()
}