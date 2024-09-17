package com.example.mymarket.presentation.screens.home

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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val marketUseCases: MarketUseCases,
    private val dataStoreManager: DataStoreManager
    ) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private var job: Job? = null

    init {
        getProducts()
    }

    private fun getProducts() {
        job?.cancel()
        job = marketUseCases.getProductsUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    dataStoreManager.updateCartCount(marketUseCases.getCartProductCountUseCase().first())
                    _state.value = _state.value.copy(
                        products = it.data ?: emptyList(),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: "")
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addToCartProduct(product: ProductDto){
        viewModelScope.launch {
            marketUseCases.addProductToCartUseCase(Product(
                id = product.id,
                name = product.name,
                description = product.description,
                createdAt = product.createdAt,
                brand = product.brand,
                model = product.model,
                image = product.image,
                price = product.price,
            )).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                        getProducts()
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun increaseCartProduct(product: ProductDto) {
        job?.cancel()
        job = marketUseCases.increaseCartProductUseCase(productId = product.id).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    getProducts()
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
                        getProducts()
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
                        getProducts()
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

    private fun insertFavoriteProduct(product: ProductDto) {
        viewModelScope.launch {
            marketUseCases.insertFavoriteProductUseCase(
                FavoriteProduct(
                name = product.name,
                id = product.id,
            )
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                        )
                        getProducts()
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun deleteFavoriteProduct(productId: String) {
        viewModelScope.launch {
            marketUseCases.deleteFavoriteProductUseCase(productId).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                        )
                        getProducts()
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.IncreaseCartProduct -> {
                increaseCartProduct(event.product)
            }

            HomeEvent.GetProducts -> {
                getProducts()
            }

            is HomeEvent.AddToCart -> {
                addToCartProduct(event.product)
            }

            is HomeEvent.DecreaseCartProduct -> {
                decreaseCartProduct(event.product)
            }

            is HomeEvent.DeleteFavorite -> {
                deleteFavoriteProduct(event.product.id)
            }
            is HomeEvent.InsertFavorite -> {
                insertFavoriteProduct(event.product)
            }

            is HomeEvent.UpdateFilterModal -> {
                _state.value = _state.value.copy(
                    filterBottomModal = event.isVisible
                )
            }
        }
    }
}