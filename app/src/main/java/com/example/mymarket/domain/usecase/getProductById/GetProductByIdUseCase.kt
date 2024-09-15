package com.example.mymarket.domain.usecase.getProductById

import android.database.sqlite.SQLiteException
import com.example.mymarket.data.datasource.locale.MarketDao
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.repository.ProductRepository
import com.example.mymarket.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import java.io.IOException
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val dao: MarketDao
) {
    suspend operator fun invoke(id: String): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        val product = dao.getProductById(id)
        if (product != null) {
            emit(Resource.Success(product))
        } else {
            emit(Resource.Error("Product not found"))
        }
    }.catch { e ->
        when (e) {
            is SQLiteException -> emit(Resource.Error("Database error: ${e.localizedMessage}"))
            is IOException -> emit(Resource.Error("I/O error: ${e.localizedMessage}"))
            else -> emit(Resource.Error("An unexpected error occurred: ${e.localizedMessage}"))
        }
    }
}