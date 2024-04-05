package ru.egordubina.foodapp.data.repository

import ru.egordubina.foodapp.data.api.CategoryApi
import ru.egordubina.foodapp.data.database.CategoryDao
import ru.egordubina.foodapp.data.models.CategoryDBO
import ru.egordubina.foodapp.domain.models.Category
import ru.egordubina.foodapp.domain.repository.CategoriesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi,
) : CategoriesRepository {
    override suspend fun loadCategories(): List<Category> {
        val response = categoryApi.loadALlCategories()
        if (response.isNotEmpty())
            response.forEach {
                categoryDao.insertAll(it.asDbo())
            }
        if (response.isEmpty())
            return categoryDao.getAll().map { it.asDomain() }
        return response
    }
}

private fun CategoryDBO.asDomain(): Category = Category(
    id.toInt(), categoryName, thumbnail, description
)

private fun Category.asDbo(): CategoryDBO = CategoryDBO(
    id.toString(), categoryName, thumbnail, description
)
