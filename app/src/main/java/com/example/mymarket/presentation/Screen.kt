package com.example.mymarket.presentation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
}