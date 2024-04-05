package ru.egordubina.foodapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.egordubina.foodapp.domain.models.Meal
import ru.egordubina.foodapp.domain.repository.MealsRepository
import javax.inject.Inject

class LoadMealsUseCase @Inject constructor(
    private val mealsRepository: MealsRepository
) {
    operator fun invoke(): Flow<List<Meal>> = mealsRepository.meals
}