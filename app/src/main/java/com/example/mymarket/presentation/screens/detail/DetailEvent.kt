package com.example.mymarket.presentation.screens.detail

import com.example.mymarket.core.viewmodel.BaseAction
import com.example.mymarket.domain.model.FavoriteProduct

sealed class DetailEvent : BaseAction{
    data class SearchProduct(val query: String): DetailEvent()
    data class GetDetail(val productId: String): DetailEvent()
    data class InsertFavorite(val product: FavoriteProduct): DetailEvent()
    object RestoreState: DetailEvent()
}