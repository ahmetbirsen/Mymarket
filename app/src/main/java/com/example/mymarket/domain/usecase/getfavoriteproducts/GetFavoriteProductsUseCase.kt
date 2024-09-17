package com.example.mymarket.domain.usecase.getfavoriteproducts

import android.database.sqlite.SQLiteException
import com.example.mymarket.data.datasource.locale.MarketDao
import com.example.mymarket.domain.model.CartProduct
import com.example.mymarket.domain.model.FavoriteDto
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.domain.repository.ProductRepository
import com.example.mymarket.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import java.io.IOException
import javax.inject.Inject

class GetFavoriteProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke() : Flow<Resource<List<FavoriteDto>>> = flow {
        try {
            emit(Resource.Loading())
            val productsFromCart = productRepository.getFavoriteProducts().first()
            emit(Resource.Success(productsFromCart))
        } catch (e: SQLiteException) {
            emit(Resource.Error("Database error: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(Resource.Error("I/O error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.localizedMessage}"))
        }
    }
}