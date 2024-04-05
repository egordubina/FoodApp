package ru.egordubina.foodapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.egordubina.foodapp.ui.screens.menu.MenuScreen
import ru.egordubina.foodapp.ui.screens.menu.MenuUiState
import ru.egordubina.foodapp.ui.screens.menu.MenuViewModel

@Composable
fun FoodAppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = FoodDestination.MENU.name,
        modifier = modifier,
    ) {
        composable(FoodDestination.MENU.name) {
            val vm: MenuViewModel = hiltViewModel()
            val uiState = vm.uiState.collectAsState()
            MenuScreen(
                uiState = uiState.value,
                onFoodItemClick = {},
                onCategoryClick = { vm.loadMealsByCategory(it) },
            )
        }
    }
}