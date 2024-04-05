package ru.egordubina.foodapp.domain.usecases

import ru.egordubina.foodapp.domain.models.Meal
import ru.egordubina.foodapp.domain.repository.MealsRepository
import javax.inject.Inject

class LoadMealsUseCase @Inject constructor(
    private val mealsRepository: MealsRepository
) {
    suspend fun loadAll(): List<Meal> = mealsRepository.loadAllMeals()
}