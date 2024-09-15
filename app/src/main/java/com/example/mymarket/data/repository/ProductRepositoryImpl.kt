package com.example.mymarket.data.repository

import com.example.mymarket.data.datasource.locale.MarketDao
import com.example.mymarket.data.datasource.remote.ProductService
import com.example.mymarket.domain.model.CartProduct
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val dao: MarketDao
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        val products = productService.getProducts()
        products.forEach { product ->
            dao.insertProduct(product)
        }
        return products
    }

    override fun getProductsFromRoom(): Flow<List<ProductDto>> {
        return dao.getProducts()
    }

    override suspend fun getProductById(id: String): ProductDto? {
        return dao.getProductById(id)
    }

    override suspend fun insertProduct(product: Product) {
        dao.insertProduct(product)
    }

    override suspend fun insertFavoriteProduct(product: FavoriteProduct) {
        dao.insertFavoriteProduct(product)
    }

    override suspend fun deleteFavoriteProduct(productId: String) {
        dao.deleteFavoriteById(productId)
    }

    override suspend fun addProductToCart(cartProduct: CartProduct) {
        dao.addProductToCart(cartProduct)
    }

    override suspend fun updateProductCount(cartProduct: CartProduct) {
        dao.updateProductCount(cartProduct)
    }

    override suspend fun increaseProductCount(cartProductId: String) {
        dao.increaseProductCount(cartProductId = cartProductId)
    }

    override suspend fun decreaseProductCount(cartProductId: String) {
        dao.decreaseProductCount(cartProductId = cartProductId)
    }

    override suspend fun removeProductFromCart(cartProductId: String) {
        dao.removeProductFromCart(cartProductId = cartProductId)
    }

    override fun getCartProducts(): Flow<List<CartProduct>> {
        return dao.getCartProducts()
    }

    override fun getCartProductCount(): Flow<Int> {
        return dao.getCartProductCount()
    }
    override suspend fun isFavorite(productId: String): Boolean {
        return dao.isProductFavorite(productId) != null
    }
}