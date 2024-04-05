package ru.egordubina.foodapp.domain.usecases

import ru.egordubina.foodapp.domain.repository.CategoriesRepository
import ru.egordubina.foodapp.ui.models.CategoryUi
import ru.egordubina.foodapp.ui.models.asUi
import javax.inject.Inject

class LoadCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
) {
    suspend fun loadAll(): List<CategoryUi> =
        categoriesRepository.loadCategories().map { it.asUi() }
}