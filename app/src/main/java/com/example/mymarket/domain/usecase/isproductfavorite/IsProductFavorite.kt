package com.example.mymarket.domain.usecase.isproductfavorite

import com.example.mymarket.data.datasource.locale.MarketDao
import com.example.mymarket.domain.repository.ProductRepository
import javax.inject.Inject

class IsProductFavorite @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: String): Boolean {
        return repository.isFavorite(productId)
    }
}