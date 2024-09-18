package com.example.mymarket.domain.usecase.getfilteredproducts

import com.example.mymarket.domain.model.FilterCriteria
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.domain.repository.ProductRepository
import com.example.mymarket.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetFilteredProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {
    operator fun invoke(
        filterCriteria: FilterCriteria
    ): Flow<Resource<List<ProductDto>>> = flow {
        try {
            emit(Resource.Loading())
            val products = productRepository.getFilterProducts(filterCriteria).first()
            emit(Resource.Success(products))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOError) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}