package com.example.mymarket.data.repository

import com.example.mymarket.data.datasource.locale.MarketDao
import com.example.mymarket.data.datasource.remote.ProductService
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product
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

    override fun getProductsFromRoom(): Flow<List<Product>> {
        return dao.getProducts()
    }

    override suspend fun getProductById(id: String): Product? {
        return dao.getProductById(id)
    }

    override suspend fun insertProduct(product: Product) {
        dao.insertProduct(product)
    }

    override suspend fun insertFavoriteProduct(product: FavoriteProduct) {
        dao.insertFavoriteProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        dao.deleteProduct(product)
    }
}