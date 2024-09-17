package com.example.mymarket.presentation.screens.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymarket.R
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.domain.usecase.MarketUseCases
import com.example.mymarket.domain.util.Resource
import com.example.mymarket.domain.util.ResourceManager
import com.example.mymarket.core.util.StringExt.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val marketUseCases: MarketUseCases,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val _state = MutableStateFlow(BasketState())
    val state: StateFlow<BasketState> = _state.asStateFlow()
    private var job: Job? = null

    fun getString(resId: Int) = resourceManager.getString(resId)

    private fun getCartProducts() {
        job?.cancel()
        job = marketUseCases.getCartProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        myCartProducts = result.data ?: emptyList(),
                        totalPrice = result.data?.sumOf { it.price.toDouble() * it.quantity } ?: 0.0
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: getString(R.string.unexpected_error)
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun increaseCartProduct(product: ProductDto) {
        job?.cancel()
        job = marketUseCases.increaseCartProductUseCase(productId = product.id).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    getCartProducts()
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: String.empty)
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun decreaseCartProduct(product: ProductDto) {
        job?.cancel()
        if (product.quantity == 1) {
            job = marketUseCases.removeProductFromCartUseCase(productId = product.id).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false)
                        getCartProducts()
                        //dataStoreManager.updateCartCount(marketUseCases.getCartProductCountUseCase().first())
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = it.message ?: String.empty)
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            job = marketUseCases.decreaseCartProductUseCase(productId = product.id).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false)
                        getCartProducts()
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = it.message ?: String.empty)
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun completeOrder() {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                marketUseCases.completeOrderUseCase()
                _state.value = _state.value.copy(isLoading = false, orderedSuccess = true)
                getCartProducts()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: String.empty)
            }
        }
    }


    fun onEvent(event: BasketEvent) {
        when (event) {
            BasketEvent.GetCartProducts -> {
                getCartProducts()
            }

            is BasketEvent.DecreaseCartProduct -> {
                decreaseCartProduct(event.product)
            }

            is BasketEvent.IncreaseCartProduct -> {
                increaseCartProduct(event.product)
            }

            BasketEvent.CompleteOrder -> {
                completeOrder()
            }
        }
    }
}