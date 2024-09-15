package com.example.mymarket.presentation.util

sealed class Screen(val route: String) {
    object DetailScreen: Screen("detail_screen")
}