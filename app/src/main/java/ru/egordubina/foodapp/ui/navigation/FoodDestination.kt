package ru.egordubina.foodapp.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.egordubina.foodapp.R

enum class FoodDestination(
    @StringRes val title: Int?,
    @DrawableRes val icon: Int?,
    @StringRes val label: Int?,
) {
    MENU(title = null, icon = R.drawable.menu, label = R.string.menu),
    DETAIL(title = null, icon = null, label = null),
    PROFILE(title = null, icon = R.drawable.profile, label = R.string.profile),
    CART(title = null, icon = R.drawable.cart, label = R.string.cart)
}

val TOP_LEVEL_DESTINATIONS =
    listOf(FoodDestination.MENU, FoodDestination.PROFILE, FoodDestination.CART)