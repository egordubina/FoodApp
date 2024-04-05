package ru.egordubina.foodapp.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.egordubina.foodapp.ui.screens.menu.MenuScreen
import ru.egordubina.foodapp.ui.screens.menu.MenuViewModel

@Composable
fun FoodAppNavigation(navController: NavHostController) {
    Scaffold(bottomBar = { NavBar(navController = navController) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FoodDestination.MENU.name,
        ) {
            composable(FoodDestination.MENU.name) {
                val vm: MenuViewModel = hiltViewModel()
                val uiState = vm.uiState.collectAsState()
                MenuScreen(
                    uiState = uiState.value,
                    onFoodItemClick = {},
                    onCategoryClick = { vm.loadMealsByCategory(it) },
                    innerPadding = innerPadding,
                )
            }
            composable(FoodDestination.PROFILE.name) {

            }
            composable(FoodDestination.CART.name) {

            }
        }
    }
}

@Composable
private fun NavBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color(0xFFF0F0F0),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        TOP_LEVEL_DESTINATIONS.forEach { dest ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = dest.icon!!),
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(id = dest.label!!)) },
                selected = currentDestination?.hierarchy?.any { it.route == dest.name } == true,
                onClick = {
                    navController.navigate(dest.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Color(0xFFFD3A69),
                    selectedIndicatorColor = Color.Transparent,
                    selectedTextColor = Color(0xFFFD3A69),
                    unselectedIconColor = Color(0xFF7B7B7B),
                    unselectedTextColor = Color(0xFF7B7B7B),
                    disabledIconColor = Color(0xFF7B7B7B),
                    disabledTextColor = Color(0xFF7B7B7B),
                )
            )
        }
    }
}