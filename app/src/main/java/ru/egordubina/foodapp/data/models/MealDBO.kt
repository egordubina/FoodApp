package ru.egordubina.foodapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealDBO(
    @PrimaryKey
    @ColumnInfo("idMeal")
    val id: String,
    @ColumnInfo("strMeal")
    val title: String,
    @ColumnInfo("strDrinkAlternate")
    val drinkAlternate: String,
    @ColumnInfo("strCategory")
    val category: String,
    @ColumnInfo("strArea")
    val country: String,
    @ColumnInfo("strInstructions")
    val instructions: String,
    @ColumnInfo("strMealThumb")
    val thumbnail: String,
    @ColumnInfo("strTags")
    val tags: String,
    @ColumnInfo("strYoutube")
    val youtube: String,
    @ColumnInfo("strIngredient1")
    val ingredient1: String,
    @ColumnInfo("strIngredient2")
    val ingredient2: String,
    @ColumnInfo("strIngredient3")
    val ingredient3: String,
    @ColumnInfo("strIngredient4")
    val ingredient4: String,
    @ColumnInfo("strIngredient5")
    val ingredient5: String,
    @ColumnInfo("strIngredient6")
    val ingredient6: String,
    @ColumnInfo("strIngredient7")
    val ingredient7: String,
    @ColumnInfo("strIngredient8")
    val ingredient8: String,
    @ColumnInfo("strIngredient9")
    val ingredient9: String,
    @ColumnInfo("strIngredient10")
    val ingredient10: String,
    @ColumnInfo("strIngredient11")
    val ingredient11: String,
    @ColumnInfo("strIngredient12")
    val ingredient12: String,
    @ColumnInfo("strIngredient13")
    val ingredient13: String,
    @ColumnInfo("strIngredient14")
    val ingredient14: String,
    @ColumnInfo("strIngredient15")
    val ingredient15: String,
    @ColumnInfo("strIngredient16")
    val ingredient16: String,
    @ColumnInfo("strIngredient17")
    val ingredient17: String,
    @ColumnInfo("strIngredient18")
    val ingredient18: String,
    @ColumnInfo("strIngredient19")
    val ingredient19: String,
    @ColumnInfo("strIngredient20")
    val ingredient20: String,
    @ColumnInfo("strMeasure1")
    val measure1: String,
    @ColumnInfo("strMeasure2")
    val measure2: String,
    @ColumnInfo("strMeasure3")
    val measure3: String,
    @ColumnInfo("strMeasure4")
    val measure4: String,
    @ColumnInfo("strMeasure5")
    val measure5: String,
    @ColumnInfo("strMeasure6")
    val measure6: String,
    @ColumnInfo("strMeasure7")
    val measure7: String,
    @ColumnInfo("strMeasure8")
    val measure8: String,
    @ColumnInfo("strMeasure9")
    val measure9: String,
    @ColumnInfo("strMeasure10")
    val measure10: String,
    @ColumnInfo("strMeasure11")
    val measure11: String,
    @ColumnInfo("strMeasure12")
    val measure12: String,
    @ColumnInfo("strMeasure13")
    val measure13: String,
    @ColumnInfo("strMeasure14")
    val measure14: String,
    @ColumnInfo("strMeasure15")
    val measure15: String,
    @ColumnInfo("strMeasure16")
    val measure16: String,
    @ColumnInfo("strMeasure17")
    val measure17: String,
    @ColumnInfo("strMeasure18")
    val measure18: String,
    @ColumnInfo("strMeasure19")
    val measure19: String,
    @ColumnInfo("strMeasure20")
    val measure20: String,
    @ColumnInfo("strSource")
    val source: String,
    @ColumnInfo("strImageSource")
    val imageSource: String,
    @ColumnInfo("strCreativeCommonsConfirmed")
    val creativeCommonsConfirmed: String,
    @ColumnInfo("dateModified")
    val dateModifier: String,
)