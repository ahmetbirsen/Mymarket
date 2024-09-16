package com.example.mymarket.domain.usecase.completeorder

import com.example.mymarket.data.datasource.locale.datastore.DataStoreManager
import com.example.mymarket.domain.repository.ProductRepository
import javax.inject.Inject

class CompleteOrderUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val dataStore : DataStoreManager
) {
    suspend operator fun invoke() {
        try {
            productRepository.clearCartProducts()
            productRepository.clearFavoriteProducts()
            dataStore.updateCartCount(0)
        } catch (e: Exception) {
            throw e
        }
    }
}
