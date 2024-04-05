package ru.egordubina.foodapp.ui.screens.menu

import ru.egordubina.foodapp.ui.models.CategoryUi
import ru.egordubina.foodapp.ui.models.MealUi

data class MenuUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val selectedCategory: String = "",
    val categoriesList: List<CategoryUi> = emptyList(),
    val foodList: List<MealUi> = emptyList(),
    val promoList: List<String> = emptyList(),
)