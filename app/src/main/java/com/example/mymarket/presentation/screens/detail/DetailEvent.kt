package com.example.mymarket.presentation.screens.detail

sealed class DetailEvent {
    data class SearchProduct(val query: String): DetailEvent()
}