package ru.egordubina.foodapp.ui.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ru.egordubina.foodapp.domain.usecases.LoadCategoriesUseCase
import ru.egordubina.foodapp.domain.usecases.LoadMealsUseCase
import ru.egordubina.foodapp.ui.models.asUi
import ru.egordubina.foodapp.ui.models.getIngredients
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val loadMealsUseCase: LoadMealsUseCase,
    private val loadCategoriesUseCase: LoadCategoriesUseCase,
) : ViewModel() {
    private var _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val uiState = combine(
        _isError,
        _isLoading,
        loadMealsUseCase(),
        loadCategoriesUseCase()
    ) { error, loading, meals, categories ->
        MenuUiState(
            isError = error,
            isLoading = loading,
            foodList = meals.map { it.asUi(ingredients = it.getIngredients()) },
            categoriesList = categories
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MenuUiState()
    )
}