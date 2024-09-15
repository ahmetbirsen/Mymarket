package com.example.mymarket.presentation.screens.detail

import com.example.mymarket.domain.model.Product

data class DetailState (
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val product: Product? = null,
    val error: String = ""
)