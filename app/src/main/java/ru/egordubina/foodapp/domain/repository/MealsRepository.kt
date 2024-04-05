package ru.egordubina.foodapp.domain.repository

import ru.egordubina.foodapp.domain.models.Meal

interface MealsRepository {
    suspend fun loadAllMeals(): List<Meal>
}