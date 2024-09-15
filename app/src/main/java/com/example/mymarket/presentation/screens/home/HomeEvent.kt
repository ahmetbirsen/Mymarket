package com.example.mymarket.presentation.screens.home

sealed class HomeEvent {
    data class SearchProduct(val query: String): HomeEvent()
}