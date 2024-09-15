package com.example.mymarket.domain.usecase.getcartproductcount

import com.example.mymarket.data.datasource.locale.MarketDao
import com.example.mymarket.domain.repository.ProductRepository
import javax.inject.Inject

class GetCartProductCountUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke() = repository.getCartProductCount()
}