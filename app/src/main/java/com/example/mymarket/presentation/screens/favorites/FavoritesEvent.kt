package com.example.mymarket.presentation.screens.favorites

import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto

sealed class FavoritesEvent {
    object GetFavoriteProducts : FavoritesEvent()
    data class InsertFavorite(val product: ProductDto) : FavoritesEvent()
    data class DeleteFavorite(val product: ProductDto) : FavoritesEvent()
}