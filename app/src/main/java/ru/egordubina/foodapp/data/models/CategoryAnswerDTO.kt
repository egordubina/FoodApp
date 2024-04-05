package ru.egordubina.foodapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryAnswerDTO(
    @SerialName("categories") val categories: List<CategoryDTO>
)