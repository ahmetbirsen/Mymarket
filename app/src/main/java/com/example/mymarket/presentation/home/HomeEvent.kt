package com.example.mymarket.presentation.home

sealed class HomeEvent {
    data class SearchProduct(val query: String): HomeEvent()
}