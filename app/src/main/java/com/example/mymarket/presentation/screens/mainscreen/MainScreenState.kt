package com.example.mymarket.presentation.screens.mainscreen

import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.domain.util.OrderType
import com.example.mymarket.domain.util.ProductOrder
import com.example.mymarket.domain.util.StringExt.empty

data class MainScreenState (
    val isLoading: Boolean = false,
    val products: List<ProductDto> = emptyList(),
    val cartCount : Int = 0,
    val searchQuery: String = String.empty,
    val productOrder: ProductOrder = ProductOrder.Date(OrderType.Ascending),
    val isOrderSectionVisible : Boolean = false,
    val error: String = ""
)