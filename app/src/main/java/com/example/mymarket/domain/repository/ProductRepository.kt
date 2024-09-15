package com.example.mymarket.domain.repository

import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    fun getProductsFromRoom(): Flow<List<Product>>

    suspend fun getProductById(id: String): Product?

    suspend fun insertProduct(product: Product)
    suspend fun insertFavoriteProduct(product: FavoriteProduct)

    suspend fun deleteProduct(product: Product)
}