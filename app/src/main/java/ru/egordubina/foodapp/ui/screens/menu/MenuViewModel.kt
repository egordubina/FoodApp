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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.egordubina.foodapp.domain.usecases.LoadCategoriesUseCase
import ru.egordubina.foodapp.domain.usecases.LoadMealsUseCase
import ru.egordubina.foodapp.ui.models.CategoryUi
import ru.egordubina.foodapp.ui.models.MealUi
import ru.egordubina.foodapp.ui.models.asUi
import ru.egordubina.foodapp.ui.models.getIngredients
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val loadMealsUseCase: LoadMealsUseCase,
    private val loadCategoriesUseCase: LoadCategoriesUseCase,
) : ViewModel() {
    private var _uiState: MutableStateFlow<MenuUiState> = MutableStateFlow(MenuUiState())
    private var _allMeals: MutableStateFlow<List<MealUi>> = MutableStateFlow(emptyList())
    private var _allCategories: MutableStateFlow<List<CategoryUi>> = MutableStateFlow(emptyList())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()
    private var job: Job? = null

    init {
        job?.cancel()
        _uiState.update { it.copy(isLoading = true) }
        job = viewModelScope.launch(Dispatchers.IO) {
            val categories = async { loadCategoriesUseCase.loadAll() }
            val meals = async { loadMealsUseCase.loadAll() }
            _allMeals.update { meals.await().map { it.asUi(it.getIngredients()) } }
            _allCategories.update { categories.await() }
            _uiState.update { state ->
                state.copy(
                    categoriesList = categories.await(),
                    foodList = meals.await().map { it.asUi(it.getIngredients()) },
                    isLoading = false,
                    promoList = listOf(
                        "https://globalsib.com/wp-content/uploads/2016/12/reklam.jpg",
                        "https://www.zastavki.com/pictures/originals/2018Food_Boiled_potatoes_with_meat_and_salad_on_a_plate_122563_.jpg"
                    )
                )
            }
        }
    }

    fun loadMealsByCategory(category: String) {
        _uiState.update { it.copy(isLoading = true) }
        if (category.isEmpty())
            _uiState.update { state ->
                state.copy(
                    foodList = _allMeals.value,
                    categoriesList = _allCategories.value,
                    isLoading = false,
                    isError = false
                )
            }
        else
            _uiState.update { state ->
                state.copy(
                    foodList = _allMeals.value.filter { it.category == category },
                    categoriesList = _allCategories.value.sortedByDescending { it.categoryName == category },
                    isLoading = false,
                    isError = false
                )
            }
    }
}