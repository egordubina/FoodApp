package ru.egordubina.foodapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.egordubina.foodapp.domain.models.Meal

interface MealsRepository {
    val meals: Flow<List<Meal>>
}