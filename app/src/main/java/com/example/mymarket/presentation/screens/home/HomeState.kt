package com.example.mymarket.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.domain.util.OrderType
import com.example.mymarket.core.util.StringExt.empty

data class HomeState (
    val isLoading: Boolean = false,
    val products: List<ProductDto>? = emptyList(),
    val searchQuery: String = String.empty,
    val isOrderSectionVisible : Boolean = false,
    val error: String? = String.empty,
    val brands : List<String> = emptyList(),
    val filteredBrands : List<String> = emptyList(),
    val filteredModels : List<String> = emptyList(),
    val models : List<String> = emptyList(),
    val filterBottomModal : Boolean = false,
    val brandTextFieldState : MutableState<String> = mutableStateOf(String.empty)
)