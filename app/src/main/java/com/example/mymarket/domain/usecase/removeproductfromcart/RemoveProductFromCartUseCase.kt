package com.example.mymarket.domain.usecase.removeproductfromcart

import android.database.sqlite.SQLiteException
import com.example.mymarket.data.datasource.locale.MarketDao
import com.example.mymarket.domain.model.CartProduct
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.repository.ProductRepository
import com.example.mymarket.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RemoveProductFromCartUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productId: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            productRepository.removeProductFromCart(cartProductId = productId)
            emit(Resource.Success(Unit))
        } catch (e: SQLiteException) {
            emit(Resource.Error("Database error: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(Resource.Error("I/O error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.localizedMessage}"))
        }
    }
}
