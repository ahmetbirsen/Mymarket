package com.example.mymarket.presentation.screens.basket

import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.presentation.screens.detail.DetailEvent

sealed class BasketEvent {
    object GetCartProducts : BasketEvent()
    data class IncreaseCartProduct(val product: ProductDto): BasketEvent()
    data class DecreaseCartProduct(val product: ProductDto): BasketEvent()
    object CompleteOrder : BasketEvent()
}