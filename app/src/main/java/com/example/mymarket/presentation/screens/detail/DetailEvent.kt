package com.example.mymarket.presentation.screens.detail

import com.example.mymarket.core.viewmodel.BaseAction
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.presentation.screens.home.HomeEvent

sealed class DetailEvent : BaseAction{
    data class GetDetail(val productId: String): DetailEvent()
    data class InsertFavorite(val product: FavoriteProduct): DetailEvent()
    data class DeleteFavorite(val productId: String): DetailEvent()
    data class AddProductToCart(val product: ProductDto): DetailEvent()
    data class IncreaseCartProduct(val product: ProductDto): DetailEvent()
    data class DecreaseCartProduct(val product: ProductDto): DetailEvent()
    data class RemoveProductFromCart(val product: Product): DetailEvent()
    object CheckFavorite: DetailEvent()
    object RestoreState: DetailEvent()
}