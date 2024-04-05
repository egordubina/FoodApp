package ru.egordubina.foodapp.data.api

import ru.egordubina.foodapp.data.models.MealDTO

interface MealApi {
    suspend fun loadAllMeals(): List<MealDTO>
}