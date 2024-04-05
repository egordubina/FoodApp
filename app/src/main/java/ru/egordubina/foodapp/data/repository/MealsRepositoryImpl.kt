package ru.egordubina.foodapp.data.repository

import ru.egordubina.foodapp.data.api.MealApi
import ru.egordubina.foodapp.data.database.MealDao
import ru.egordubina.foodapp.data.models.MealDBO
import ru.egordubina.foodapp.data.models.asDomain
import ru.egordubina.foodapp.domain.models.Meal
import ru.egordubina.foodapp.domain.repository.MealsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsRepositoryImpl @Inject constructor(
    private val mealDao: MealDao,
    private val mealApi: MealApi,
) : MealsRepository {
    override suspend fun loadAllMeals(): List<Meal> {
        val response = mealApi.loadAllMeals().map { it.asDomain() }
        if (response.isNotEmpty())
            response.forEach { mealDao.insert(it.asDbo()) }
        if (response.isEmpty())
            return mealDao.getAll().map { it.asDomain() }
        return response
    }
}

private fun Meal.asDbo(): MealDBO = MealDBO(
    id = id.toString(),
    title = title,
    drinkAlternate = drinkAlternate,
    category = category,
    country = country,
    instructions = instructions,
    thumbnail = thumbnail,
    tags = tags,
    youtube = youtube,
    ingredient1 = ingredient1,
    ingredient2 = ingredient2,
    ingredient3 = ingredient3,
    ingredient4 = ingredient4,
    ingredient5 = ingredient5,
    ingredient6 = ingredient6,
    ingredient7 = ingredient7,
    ingredient8 = ingredient8,
    ingredient9 = ingredient9,
    ingredient10 = ingredient10,
    ingredient11 = ingredient11,
    ingredient12 = ingredient12,
    ingredient13 = ingredient13,
    ingredient14 = ingredient14,
    ingredient15 = ingredient15,
    ingredient16 = ingredient16,
    ingredient17 = ingredient17,
    ingredient18 = ingredient18,
    ingredient19 = ingredient19,
    ingredient20 = ingredient20,
    measure1 = measure1,
    measure2 = measure2,
    measure3 = measure3,
    measure4 = measure4,
    measure5 = measure5,
    measure6 = measure6,
    measure7 = measure7,
    measure8 = measure8,
    measure9 = measure9,
    measure10 = measure10,
    measure11 = measure11,
    measure12 = measure12,
    measure13 = measure13,
    measure14 = measure14,
    measure15 = measure15,
    measure16 = measure16,
    measure17 = measure17,
    measure18 = measure18,
    measure19 = measure19,
    measure20 = measure20,
    source = source,
    imageSource = imageSource,
    creativeCommonsConfirmed = creativeCommonsConfirmed,
    dateModifier = dateModifier
)

fun MealDBO.asDomain(): Meal = Meal(
    id = id.toInt(),
    title = title,
    drinkAlternate = drinkAlternate,
    category = category,
    country = country,
    instructions = instructions,
    thumbnail = thumbnail,
    tags = tags,
    youtube = youtube,
    ingredient1 = ingredient1,
    ingredient2 = ingredient2,
    ingredient3 = ingredient3,
    ingredient4 = ingredient4,
    ingredient5 = ingredient5,
    ingredient6 = ingredient6,
    ingredient7 = ingredient7,
    ingredient8 = ingredient8,
    ingredient9 = ingredient9,
    ingredient10 = ingredient10,
    ingredient11 = ingredient11,
    ingredient12 = ingredient12,
    ingredient13 = ingredient13,
    ingredient14 = ingredient14,
    ingredient15 = ingredient15,
    ingredient16 = ingredient16,
    ingredient17 = ingredient17,
    ingredient18 = ingredient18,
    ingredient19 = ingredient19,
    ingredient20 = ingredient20,
    measure1 = measure1,
    measure2 = measure2,
    measure3 = measure3,
    measure4 = measure4,
    measure5 = measure5,
    measure6 = measure6,
    measure7 = measure7,
    measure8 = measure8,
    measure9 = measure9,
    measure10 = measure10,
    measure11 = measure11,
    measure12 = measure12,
    measure13 = measure13,
    measure14 = measure14,
    measure15 = measure15,
    measure16 = measure16,
    measure17 = measure17,
    measure18 = measure18,
    measure19 = measure19,
    measure20 = measure20,
    source = source,
    imageSource = imageSource,
    creativeCommonsConfirmed = creativeCommonsConfirmed,
    dateModifier = dateModifier,
    minPrice = 0,
)
