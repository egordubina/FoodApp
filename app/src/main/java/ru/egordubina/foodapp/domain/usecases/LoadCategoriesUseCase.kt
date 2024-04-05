package ru.egordubina.foodapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.egordubina.foodapp.ui.models.CategoryUi
import ru.egordubina.foodapp.ui.models.asUi
import ru.egordubina.foodapp.domain.repository.CategoriesRepository
import javax.inject.Inject

class LoadCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
) {
    operator fun invoke(): Flow<List<CategoryUi>> =
        categoriesRepository.categories.map { it.map { it.asUi() } }
}