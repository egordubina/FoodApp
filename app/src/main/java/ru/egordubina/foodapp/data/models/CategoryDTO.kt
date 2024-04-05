package ru.egordubina.foodapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.egordubina.foodapp.domain.models.Category

@Serializable
data class CategoryDTO(
    @SerialName("idCategory") val id: String,
    @SerialName("strCategory") val categoryName: String,
    @SerialName("strCategoryThumb") val thumbnail: String,
    @SerialName("strCategoryDescription") val description: String,
)

fun CategoryDTO.asDomain(): Category = Category(
    id = id.toInt(),
    categoryName = categoryName,
    thumbnail = thumbnail,
    description = description,
)