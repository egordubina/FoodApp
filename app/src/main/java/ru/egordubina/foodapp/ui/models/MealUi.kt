package ru.egordubina.foodapp.ui.models

import ru.egordubina.foodapp.domain.models.Meal

data class MealUi(
    val id: Int,
    val title: String,
    val drinkAlternate: String,
    val category: String,
    val country: String,
    val instructions: String,
    val thumbnail: String,
    val tags: String,
    val youtube: String,
    val ingredients: List<String>,
    val measure1: String,
    val measure2: String,
    val measure3: String,
    val measure4: String,
    val measure5: String,
    val measure6: String,
    val measure7: String,
    val measure8: String,
    val measure9: String,
    val measure10: String,
    val measure11: String,
    val measure12: String,
    val measure13: String,
    val measure14: String,
    val measure15: String,
    val measure16: String,
    val measure17: String,
    val measure18: String,
    val measure19: String,
    val measure20: String,
    val source: String,
    val imageSource: String,
    val creativeCommonsConfirmed: String,
    val dateModifier: String,
    val minPrice: String,
)

fun Meal.asUi(ingredients: List<String>): MealUi = MealUi(
    id = id,
    title = title,
    drinkAlternate = drinkAlternate,
    category = category,
    country = country,
    instructions = instructions,
    thumbnail = thumbnail,
    tags = tags,
    youtube = youtube,
    ingredients = ingredients,
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
    minPrice = minPrice.toRub(),
)

fun Meal.getIngredients(): List<String> = listOf(
    this.ingredient1,
    this.ingredient2,
    this.ingredient3,
    this.ingredient4,
    this.ingredient5,
    this.ingredient6,
    this.ingredient7,
    this.ingredient8,
    this.ingredient9,
    this.ingredient10,
    this.ingredient11,
    this.ingredient12,
    this.ingredient13,
    this.ingredient14,
    this.ingredient15,
    this.ingredient16,
    this.ingredient17,
    this.ingredient18,
    this.ingredient19,
    this.ingredient20,
)

/**
 * Возвращает строку, приведённую к формату стоиости с валютным знаком
 * @return Строка с разделенным числом через пробел и знак рубля
 * */
private fun Int.toRub(): String {
    val result = StringBuilder()
    val reversNumber = this.toString().reversed()
    reversNumber.forEachIndexed { index, char ->
        result.append(char)
        if ((index + 1) % 3 == 0 && (index + 1) != reversNumber.length)
            result.append(" ")
    }
    result.reverse()
    return "От $result ₽"
}