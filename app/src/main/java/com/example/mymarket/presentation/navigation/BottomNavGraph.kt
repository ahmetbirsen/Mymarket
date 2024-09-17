package com.example.mymarket.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mymarket.presentation.screens.basket.views.BasketScreen
import com.example.mymarket.presentation.util.Screen
import com.example.mymarket.presentation.screens.detail.views.DetailScreen
import com.example.mymarket.presentation.screens.favorites.views.FavoritesScreen
import com.example.mymarket.presentation.screens.home.views.HomeScreen

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
                navController = navController
            )
        }
        composable(route = BottomBarScreen.Basket.route) {
            BasketScreen(
                navController = navController
            )
        }
        composable(route = BottomBarScreen.Favorite.route) {
            FavoritesScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
        }
        composable(route = Screen.DetailScreen.route +
                "/{productId}",
            arguments = listOf(
                navArgument(name = "productId") {
                    type = NavType.StringType
                }
            )
            ) {
            DetailScreen(
                navController = navController
            )
        }
    }
}