package com.example.mymarket.presentation.screens.detail

import com.example.mymarket.core.enums.UiState
import com.example.mymarket.core.viewmodel.BaseViewState
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.util.StringExt.empty

data class DetailState (
    override val uiState: UiState = UiState.LOADING,
    val products: List<Product> = emptyList(),
    val productId : String = String.empty,
    val product: Product? = null,
    val message: String = String.empty,
    val isLoading: Boolean = false
) : BaseViewState