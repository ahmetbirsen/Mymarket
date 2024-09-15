package com.example.mymarket.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}