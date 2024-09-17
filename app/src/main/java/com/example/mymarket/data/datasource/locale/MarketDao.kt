package com.example.mymarket.data.datasource.locale

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mymarket.domain.model.CartProduct
import com.example.mymarket.domain.model.FavoriteDto
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketDao {

    @Query("""SELECT 
            product.*, 
            cart_products.quantity,
            EXISTS (SELECT id FROM favorite_products WHERE id = product.id)
             AS isFavorite
        FROM product 
        LEFT JOIN cart_products ON product.id = cart_products.id
        LEFT JOIN favorite_products ON product.id = favorite_products.id
        """)
    fun getProducts(): Flow<List<ProductDto>>

    @Query("""SELECT 
        product.*, 
        cart_products.quantity FROM product 
        LEFT JOIN cart_products ON product.id = cart_products.id
        WHERE product.id = :id
        """)
    suspend fun getProductById(id: String): ProductDto?


    @Query("""SELECT 
            product.*, 
            cart_products.quantity
        FROM product
    INNER JOIN cart_products ON product.id = cart_products.id
        """)
    fun getCartProducts(): Flow<List<ProductDto>>

    @Query("""SELECT 
            product.*,
             favorite_products.id AS isFavorite
        FROM product
    INNER JOIN favorite_products ON product.id = favorite_products.id
        """)
    fun getFavoriteProducts(): Flow<List<FavoriteDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteProduct(product: FavoriteProduct)

    @Query("SELECT * FROM favorite_products WHERE id = :productId LIMIT 1")
    suspend fun isProductFavorite(productId: String): FavoriteProduct?

    @Query("DELETE FROM favorite_products WHERE id = :productId")
    suspend fun deleteFavoriteById(productId: String)

    @Insert
    suspend fun addProductToCart(cartProduct: CartProduct)

    @Update
    suspend fun updateProductCount(cartProduct: CartProduct)

    @Query("UPDATE cart_products SET quantity = quantity + 1 WHERE id = :cartProductId")
    suspend fun increaseProductCount(cartProductId: String)

    @Query("DELETE FROM cart_products WHERE id = :cartProductId")
    suspend fun removeProductFromCart(cartProductId: String)

    @Query("UPDATE cart_products SET quantity = quantity - 1 WHERE id = :cartProductId")
    suspend fun decreaseProductCount(cartProductId: String)

    @Query("SELECT COUNT(*) FROM cart_products")
    fun getCartProductCount(): Flow<Int>

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM cart_products")
    suspend fun clearCartProducts()

    @Query("DELETE FROM favorite_products")
    suspend fun clearFavoriteProducts()
}