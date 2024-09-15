package com.example.mymarket.presentation.screens.home

import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto

sealed class HomeEvent {
    data class IncreaseCartProduct(val product: ProductDto): HomeEvent()
    data class DecreaseCartProduct(val product: ProductDto): HomeEvent()
    object GetProducts: HomeEvent()
    data class AddToCart(val product: ProductDto): HomeEvent()
    data class InsertFavorite(val product: ProductDto): HomeEvent()
    data class DeleteFavorite(val product: ProductDto): HomeEvent()
}