package com.example.mymarket.domain.usecase.completeorder

import com.example.mymarket.domain.repository.ProductRepository
import javax.inject.Inject

class CompleteOrderUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke() {
        try {
            productRepository.clearCartProducts()
            productRepository.clearFavoriteProducts()
        } catch (e: Exception) {
            throw e
        }
    }
}
