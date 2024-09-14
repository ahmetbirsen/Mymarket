package com.example.mymarket.domain.repository

import com.example.mymarket.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
}