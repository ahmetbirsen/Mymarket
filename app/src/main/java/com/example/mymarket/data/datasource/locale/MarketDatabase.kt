package com.example.mymarket.data.datasource.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product

@Database(
    entities = [Product::class, FavoriteProduct::class],
    version = 1,
    exportSchema = false
)
abstract class MarketDatabase: RoomDatabase() {
    abstract fun marketDao(): MarketDao

    companion object {
        const val DATABASE_NAME = "market_database"
    }
}