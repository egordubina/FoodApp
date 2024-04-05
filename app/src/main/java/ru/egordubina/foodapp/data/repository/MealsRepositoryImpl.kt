package ru.egordubina.foodapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.egordubina.foodapp.data.api.MealApi
import ru.egordubina.foodapp.data.models.asDomain
import ru.egordubina.foodapp.domain.models.Meal
import ru.egordubina.foodapp.domain.repository.MealsRepository
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val mealApi: MealApi
) : MealsRepository {
    override val meals: Flow<List<Meal>>
        get() = flow { emit(mealApi.loadAllMeals().map { it.asDomain() }) }.flowOn(Dispatchers.IO)
}