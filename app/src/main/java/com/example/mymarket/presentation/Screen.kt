package com.example.mymarket.presentation

sealed class Screen(val route: String) {
    object DetailScreen: Screen("detail_screen")
}