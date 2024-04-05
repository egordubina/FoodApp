package ru.egordubina.foodapp.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.egordubina.foodapp.data.models.MealAnswerDTO
import ru.egordubina.foodapp.data.models.MealDTO
import javax.inject.Inject

class MealApiImpl @Inject constructor(
    private val client: HttpClient,
    private val baseUrl: String,
) : MealApi {
    override suspend fun loadAllMeals(): List<MealDTO> {
        return try {
            val response = client.get("$baseUrl/search.php?s").bodyAsText()
            Json.decodeFromString<MealAnswerDTO>(response).meals
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}