package ru.egordubina.foodapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import ru.egordubina.foodapp.data.api.CategoryApi
import ru.egordubina.foodapp.domain.models.Category
import ru.egordubina.foodapp.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoryApi: CategoryApi
) : CategoriesRepository {
    override val categories: Flow<List<Category>>
        get() = flow { emit(categoryApi.loadALlCategories()) }.flowOn(Dispatchers.IO)
}