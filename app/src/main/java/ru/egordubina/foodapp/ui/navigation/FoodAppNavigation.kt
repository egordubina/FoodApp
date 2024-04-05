package ru.egordubina.foodapp.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.egordubina.foodapp.ui.screens.cart.CartScreen
import ru.egordubina.foodapp.ui.screens.menu.MenuScreen
import ru.egordubina.foodapp.ui.screens.menu.MenuViewModel
import ru.egordubina.foodapp.ui.screens.profile.Profile

@Composable
fun FoodAppNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        bottomBar = {
            AnimatedVisibility(TOP_LEVEL_DESTINATIONS.any { it.name == currentDestination?.route }) {
                NavBar(
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FoodDestination.MENU.name,
            modifier = Modifier
                .padding(PaddingValues(bottom = innerPadding.calculateBottomPadding()))
                .fillMaxSize()
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
            composable(FoodDestination.PROFILE.name) {
                Profile()
            }
            composable(FoodDestination.CART.name) {
                CartScreen()
            }
        }
    }
}

@Composable
private fun NavBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    NavigationBar(containerColor = Color(0xFFF0F0F0)) {
        TOP_LEVEL_DESTINATIONS.forEach { dest ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = dest.icon!!),
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(id = dest.label!!)) },
                selected = currentDestination?.route == dest.name,
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