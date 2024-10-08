package com.example.mymarket.data.datasource.remote

import com.example.mymarket.domain.model.Product
import retrofit2.http.GET

interface ProductService {

    @GET("products")
    suspend fun getProducts(): List<Product>
}