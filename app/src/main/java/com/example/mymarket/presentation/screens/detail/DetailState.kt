package com.example.mymarket.presentation.screens.detail

import com.example.mymarket.core.enums.UiState
import com.example.mymarket.core.viewmodel.BaseViewState
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.core.util.StringExt.empty

data class DetailState (
    override val uiState: UiState = UiState.LOADING,
    val productId : String = String.empty,
    val product: ProductDto = ProductDto(),
    val totalPrice: String? = null,
    val message: String = String.empty,
    val isLoading: Boolean = false,
    val isFavorite : Boolean = false
) : BaseViewState