package ru.egordubina.foodapp.data.repository

import ru.egordubina.foodapp.data.api.MealApi
import ru.egordubina.foodapp.data.models.asDomain
import ru.egordubina.foodapp.domain.models.Meal
import ru.egordubina.foodapp.domain.repository.MealsRepository
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val mealApi: MealApi,
) : MealsRepository {
    override suspend fun loadAllmeals(): List<Meal> =
        mealApi.loadAllMeals().map { it.asDomain() }
}