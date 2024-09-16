package com.example.mymarket.presentation.screens.mainscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymarket.R
import com.example.mymarket.data.datasource.locale.datastore.DataStoreManager
import com.example.mymarket.domain.model.CartProduct
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.domain.usecase.MarketUseCases
import com.example.mymarket.domain.usecase.getproducts.GetProductsUseCase
import com.example.mymarket.domain.util.Resource
import com.example.mymarket.domain.util.StringExt.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    dataStoreManager: DataStoreManager
    ) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    private val cartFlow : Flow<Int> = dataStoreManager.getCartCount()

    private fun observeCartCount() {
        cartFlow.onEach {
            _state.value = _state.value.copy(
                cartCount = it
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            MainScreenEvent.ObserveCartCount -> observeCartCount()
        }
    }
}