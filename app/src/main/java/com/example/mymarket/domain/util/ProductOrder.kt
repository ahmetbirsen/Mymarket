package com.example.mymarket.domain.util

sealed class ProductOrder(val orderType: OrderType) {
    class Date(orderType: OrderType) : ProductOrder(orderType)
    class Price(orderType: OrderType) : ProductOrder(orderType)

    fun copy(orderType: OrderType): ProductOrder {
        return when(this) {
            is Date -> Date(orderType)
            is Price -> Price(orderType)
        }
    }
}