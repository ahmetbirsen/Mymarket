package com.example.mymarket.presentation.screens.favorites

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.core.util.StringExt.empty

data class FavoritesState (
    val isLoading: Boolean = false,
    val favoriteProducts: List<ProductDto> = emptyList(),
    val searchQuery: String = String.empty,
    val isOrderSectionVisible : Boolean = false,
    val error: String = "",
    val filterBottomModal : Boolean = false,
    val brandTextFieldState : MutableState<String> = mutableStateOf(String.empty)
)