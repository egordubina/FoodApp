package ru.egordubina.foodapp.data.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.egordubina.foodapp.data.models.CategoryAnswerDTO
import ru.egordubina.foodapp.data.models.CategoryDTO
import ru.egordubina.foodapp.data.models.asDomain
import ru.egordubina.foodapp.domain.models.Category
import javax.inject.Inject

class CategoryApiImpl @Inject constructor(
    private val client: HttpClient,
    private val baseUrl: String
) : CategoryApi {
    override suspend fun loadALlCategories(): List<Category> {
        return try {
            val response = client.get("$baseUrl/categories.php").bodyAsText()
            Json.decodeFromString<CategoryAnswerDTO>(response).categories.map { it.asDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}