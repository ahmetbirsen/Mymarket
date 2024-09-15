package com.example.mymarket.domain.usecase.getproducts

import com.example.mymarket.data.datasource.locale.MarketDao
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.domain.repository.ProductRepository
import com.example.mymarket.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val dao: MarketDao
) {
    operator fun invoke() : Flow<Resource<List<ProductDto>>> = flow {
        try {
            emit(Resource.Loading())
            val productsFromApi = productRepository.getProducts()
            productsFromApi.forEach { product ->
                dao.insertProduct(product)
            }
            val productsFromRoom = dao.getProducts().first()
            emit(Resource.Success(productsFromRoom))
        } catch (e: IOError) {
            emit(Resource.Error(message = "No internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An error occurred"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An error occurred"))
        }
    }
}