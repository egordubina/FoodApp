package ru.egordubina.foodapp.ui.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.egordubina.foodapp.domain.usecases.LoadCategoriesUseCase
import ru.egordubina.foodapp.domain.usecases.LoadMealsUseCase
import ru.egordubina.foodapp.ui.models.CategoryUi
import ru.egordubina.foodapp.ui.models.MealUi
import ru.egordubina.foodapp.ui.models.asUi
import ru.egordubina.foodapp.ui.models.getIngredients
import java.util.Locale.filter
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val loadMealsUseCase: LoadMealsUseCase,
    private val loadCategoriesUseCase: LoadCategoriesUseCase,
) : ViewModel() {
    private var _uiState: MutableStateFlow<MenuUiState> = MutableStateFlow(MenuUiState())
    private var _allMeals: MutableStateFlow<List<MealUi>> = MutableStateFlow(emptyList())
    private var _allCategores: MutableStateFlow<List<CategoryUi>> = MutableStateFlow(emptyList())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()
    private var job: Job? = null

    init {
        job?.cancel()
        _uiState.update { it.copy(isLoading = true) }
        job = viewModelScope.launch(Dispatchers.IO) {
            val categories = async { loadCategoriesUseCase.loadAll() }
            val meals = async { loadMealsUseCase.loadAll() }
            _allMeals.update { meals.await().map { it.asUi(it.getIngredients()) } }
            _allCategores.update { categories.await() }
            _uiState.update {
                it.copy(
                    categoriesList = categories.await(),
                    foodList = meals.await().map { it.asUi(it.getIngredients()) },
                    isLoading = false
                )
            }
        }
    }

    fun loadMealsByCategory(category: String) {
        if (category.isEmpty())
            _uiState.update {
                it.copy(
                    foodList = _allMeals.value,
                    categoriesList = _allCategores.value
                )
            }
        else
            _uiState.update {
                it.copy(
                    foodList = _allMeals.value.filter { it.category == category },
                    categoriesList = _allCategores.value.sortedByDescending { it.categoryName == category }
                )
            }
    }
}