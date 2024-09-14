package com.example.mymarket.presentation.home

import com.example.mymarket.domain.model.Product

data class HomeState (
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = ""
)