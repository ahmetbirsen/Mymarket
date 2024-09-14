package com.example.mymarket.domain.usecase.getproducts

import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.repository.ProductRepository
import com.example.mymarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    fun executeGetProducts() : Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val products = productRepository.getProducts()
            emit(Resource.Success(products))
        } catch (e: IOError) {
            emit(Resource.Error(message = "No internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An error occurred"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An error occurred"))
        }
    }
}