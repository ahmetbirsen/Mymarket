package com.example.mymarket.core.util

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

const val CART_DATA_STORE_NAME = "cart_data_store"

val Context.dataStore by preferencesDataStore(name = CART_DATA_STORE_NAME)