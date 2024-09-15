package com.example.mymarket.domain.usecase.getproductsfromroom

import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.repository.ProductRepository
import com.example.mymarket.domain.util.OrderType
import com.example.mymarket.domain.util.ProductOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductsFromRoom @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(
        productOrder : ProductOrder = ProductOrder.Date(OrderType.Descending)
    ) : Flow<List<Product>>{
        return productRepository.getProductsFromRoom().map { products ->
            when(productOrder.orderType){
                is OrderType.Ascending -> {
                    when(productOrder){
                        is ProductOrder.Date -> products.sortedBy { it.createdAt }
                        is ProductOrder.Price -> products.sortedBy { it.price }
                    }
                }
                is OrderType.Descending -> {
                    when(productOrder){
                        is ProductOrder.Date -> products.sortedByDescending { it.createdAt }
                        is ProductOrder.Price -> products.sortedByDescending { it.price }
                    }
                }
            }
        }
    }
}