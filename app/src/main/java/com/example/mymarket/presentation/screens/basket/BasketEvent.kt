package com.example.mymarket.presentation.screens.basket

sealed class BasketEvent {
    data class SearchProduct(val query: String): BasketEvent()
}