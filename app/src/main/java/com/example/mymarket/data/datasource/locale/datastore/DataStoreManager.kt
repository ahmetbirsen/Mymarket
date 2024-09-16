package com.example.mymarket.data.datasource.locale.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) : DataStoreManagement {

    companion object {
        private const val CART_COUNT = "cart_count"
        val cartCount = intPreferencesKey(CART_COUNT)
    }

    override suspend fun updateCartCount(count: Int) {
        dataStore.edit { preferences ->
            preferences[cartCount] = count
        }
    }

    override fun getCartCount(): Flow<Int> {
        val count = dataStore.data.map { preferences ->
            preferences[cartCount] ?: 0
        }
        return count
    }
}