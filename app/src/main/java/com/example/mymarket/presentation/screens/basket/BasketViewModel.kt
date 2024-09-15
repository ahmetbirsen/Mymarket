package com.example.mymarket.presentation.screens.basket

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymarket.domain.usecase.getproducts.GetProductsUseCase
import com.example.mymarket.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getProductUSeCase: GetProductsUseCase
): ViewModel() {

    private val _state = mutableStateOf(BasketState())
    val state : State<BasketState> = _state

    private var job : Job? = null

    init {
        getProducts()
    }

    private fun getProducts() {
        job = getProductUSeCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = BasketState(products = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = BasketState(error = it.message ?: "")
                }

                is Resource.Loading -> {
                    _state.value = BasketState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: BasketEvent) {
        when (event) {
            is BasketEvent.SearchProduct -> {
                getProducts()
            }
        }
    }
}