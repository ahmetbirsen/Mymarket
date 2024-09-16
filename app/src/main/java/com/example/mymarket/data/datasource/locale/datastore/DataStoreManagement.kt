package com.example.mymarket.data.datasource.locale.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreManagement {
    suspend fun updateCartCount(count: Int)
    fun getCartCount(): Flow<Int>
}