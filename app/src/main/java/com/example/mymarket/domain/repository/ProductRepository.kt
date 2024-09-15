package com.example.mymarket.domain.repository

import com.example.mymarket.domain.model.CartProduct
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    fun getProductsFromRoom(): Flow<List<ProductDto>>

    suspend fun getProductById(id: String): ProductDto?

    suspend fun insertProduct(product: Product)
    suspend fun insertFavoriteProduct(product: FavoriteProduct)
    suspend fun deleteFavoriteProduct(productId: String)

    suspend fun addProductToCart(cartProduct: CartProduct)
    suspend fun updateProductCount(cartProduct: CartProduct)
    suspend fun increaseProductCount(cartProductId: String)
    suspend fun decreaseProductCount(cartProductId: String)
    suspend fun removeProductFromCart(cartProductId: String)
    fun getCartProducts(): Flow<List<CartProduct>>
    fun getCartProductCount(): Flow<Int>
    suspend fun isFavorite(productId: String): Boolean

}