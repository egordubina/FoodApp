package ru.egordubina.foodapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.egordubina.foodapp.domain.models.Meal

interface MealsRepository {
    suspend fun loadAllmeals(): List<Meal>
}