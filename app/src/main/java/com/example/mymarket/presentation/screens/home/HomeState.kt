package com.example.mymarket.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.domain.util.OrderType
import com.example.mymarket.domain.util.ProductOrder
import com.example.mymarket.domain.util.StringExt.empty

data class HomeState (
    val isLoading: Boolean = false,
    val products: List<ProductDto> = emptyList(),
    val searchQuery: String = String.empty,
    val productOrder: ProductOrder = ProductOrder.Date(OrderType.Ascending),
    val isOrderSectionVisible : Boolean = false,
    val error: String = "",
    val filterBottomModal : Boolean = false,
    val brandTextFieldState : MutableState<String> = mutableStateOf(String.empty)
)