package com.example.mymarket.presentation.screens.mainscreen

import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto

sealed class MainScreenEvent {
    object ObserveCartCount : MainScreenEvent()
}