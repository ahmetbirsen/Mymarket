package com.example.mymarket.presentation.screens.basket

import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto

data class BasketState (
    val isLoading: Boolean = false,
    val products: List<ProductDto> = emptyList(),
    val product: Product? = null,
    val error: String = ""
)