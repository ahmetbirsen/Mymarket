package com.example.mymarket.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.mymarket.R

sealed class BottomBarScreen(
    val route: String,
    @DrawableRes val iconRes: Int
) {
    object Home : BottomBarScreen(
        route = "home_screen",
        iconRes = R.drawable.ic_home
    )

    object Basket : BottomBarScreen(
        route = "basket_screen",
        iconRes = R.drawable.ic_basket
    )

    object Favorite : BottomBarScreen(
        route = "favorite_screen",
        iconRes = R.drawable.ic_favorites
    )

    object Profile : BottomBarScreen(
        route = "profile_screen",
        iconRes = R.drawable.ic_profile
    )
}