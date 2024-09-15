package com.example.mymarket.presentation.screens.home

import com.example.mymarket.domain.model.Product
import com.example.mymarket.util.StringExt.empty

data class HomeState (
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val searchQuery: String = String.empty,
    val error: String = ""
)