package ru.egordubina.foodapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealAnswerDTO(
    @SerialName("meals") val meals: List<MealDTO>
)