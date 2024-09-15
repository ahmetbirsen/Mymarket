package com.example.mymarket.presentation.screens.basket

import com.example.mymarket.domain.model.Product

data class BasketState (
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val product: Product? = null,
    val error: String = ""
)