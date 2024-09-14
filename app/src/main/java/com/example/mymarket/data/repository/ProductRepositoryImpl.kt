package com.example.mymarket.data.repository

import com.example.mymarket.data.remote.ProductService
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return productService.getProducts()
    }
}