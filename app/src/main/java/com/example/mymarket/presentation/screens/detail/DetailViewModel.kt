package com.example.mymarket.presentation.screens.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymarket.core.viewmodel.BaseViewModel
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.usecase.MarketUseCases
import com.example.mymarket.domain.usecase.getProductById.GetProductByIdUseCase
import com.example.mymarket.domain.usecase.getproducts.GetProductsUseCase
import com.example.mymarket.domain.util.Resource
import com.example.mymarket.domain.util.StringExt.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val marketUseCases: MarketUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state : State<DetailState> = _state

    private var job : Job? = null

    init {
        savedStateHandle.get<String>("productId")?.let { productId ->
            viewModelScope.launch {
                _state.value = DetailState(productId = productId)
                marketUseCases.getProductById(id = DetailState().productId).also { product ->
                    _state.value = state.value.copy(product = product.first().data)
                }
            }
        }
    }

    private  fun getProductById(productId: String) {
        viewModelScope.launch {
            job?.cancel()
            job = marketUseCases.getProductById(id = productId).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = DetailState(product = it.data)
                    }

                    is Resource.Error -> {
                        _state.value = DetailState(message = it.message ?: String.empty)
                    }

                    is Resource.Loading -> {
                        _state.value = DetailState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun insertFavoriteProduct(product: FavoriteProduct) {
        viewModelScope.launch {
            marketUseCases.insertFavoriteProductUseCase(product).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = DetailState(message = "Product added to favorites")
                    }

                    is Resource.Error -> {
                        _state.value = DetailState(message = it.message ?: String.empty)
                    }

                    is Resource.Loading -> {
                        _state.value = DetailState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.SearchProduct -> {
                //getProducts()
            }

            is DetailEvent.GetDetail -> {
                getProductById(event.productId)
            }

            is DetailEvent.InsertFavorite -> {
                insertFavoriteProduct(event.product)
            }

            DetailEvent.RestoreState -> {
                _state.value = state.value.copy(message = String.empty)
            }
        }
    }
}
