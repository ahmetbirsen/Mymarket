package com.example.mymarket.data.datasource.locale

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketDao {

    @Query("SELECT * FROM product")
    fun getProducts() : Flow<List<Product>>

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id: String): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteProduct(product: FavoriteProduct)

    @Delete
    suspend fun deleteProduct(product: Product)
}