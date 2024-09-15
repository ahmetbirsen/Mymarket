package com.example.mymarket.presentation.screens.basket

import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto

data class BasketState (
    val isLoading: Boolean = false,
    val myCartProducts: List<ProductDto> = emptyList(),
    val totalPrice : Double = 0.0,
    val product: ProductDto = ProductDto(),
    val error: String = "",
    val orderedSuccess : Boolean = false
)