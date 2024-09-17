package com.example.mymarket.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymarket.R
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.Product
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val marketUseCases: MarketUseCases,
    private val resourceManager: ResourceManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private var job: Job? = null

    fun getString(resId: Int) = resourceManager.getString(resId)

    init {
        savedStateHandle.get<String>("productId")?.let { productId ->
            viewModelScope.launch {
                _state.value = _state.value.copy(productId = productId)
                checkIsFavorite(productId = productId)
                marketUseCases.getProductById(id = productId).also { product ->
                    _state.value = _state.value.copy(product = product.first().data ?: ProductDto())
                }
            }
        }
    }

    private fun checkIsFavorite(productId: String) {
        viewModelScope.launch {
            val isFavorite = marketUseCases.isProductFavorite(productId)
            _state.value = _state.value.copy(isFavorite = isFavorite)
        }
    }

    private fun deleteFavoriteProduct(productId: String) {
        viewModelScope.launch {
            marketUseCases.deleteFavoriteProductUseCase(productId).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            message = getString(R.string.remove_favorite_successfully),
                            isLoading = false,
                            isFavorite = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            message = it.message ?: String.empty,
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

    private fun getProductById(productId: String) {
        viewModelScope.launch {
            job?.cancel()
            job = marketUseCases.getProductById(id = productId).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            product = it.data ?: ProductDto(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            message = it.message ?: String.empty,
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

    private fun insertFavoriteProduct(product: FavoriteProduct) {
        viewModelScope.launch {
            marketUseCases.insertFavoriteProductUseCase(product).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            message = getString(R.string.add_favorite_successfully),
                            isLoading = false,
                            isFavorite = true
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            message = it.message ?: String.empty,
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

    private fun addToCartProduct(product: ProductDto) {
        viewModelScope.launch {
            marketUseCases.addProductToCartUseCase(Product(
                name = product.name,
                id = product.id,
                price = product.price,
                brand = product.brand,
                model = product.model,
                image = product.image,
                createdAt = product.createdAt,
                description = product.description
            )).onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            message = getString(R.string.add_to_cart_successfully),
                            isLoading = false
                        )
                        getProductById(product.id)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            message = it.message ?: String.empty,
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

    private fun removeProductFromCart(product: Product) {
        marketUseCases.removeProductFromCartUseCase(productId = product.id).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        message = getString(R.string.remove_from_cart_successfully),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        message = it.message ?: String.empty,
                        isLoading = false
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
                    getProductById(product.id)
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(message = it.message ?: String.empty)
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
                        getProductById(product.id)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(message = it.message ?: String.empty)
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
                        getProductById(product.id)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(message = it.message ?: String.empty)
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {

            is DetailEvent.GetDetail -> {
                getProductById(event.productId)
            }

            is DetailEvent.InsertFavorite -> {
                insertFavoriteProduct(event.product)
            }

            DetailEvent.RestoreState -> {
                _state.value = state.value.copy(message = String.empty)
            }

            is DetailEvent.DeleteFavorite -> {
                deleteFavoriteProduct(event.productId)
            }

            DetailEvent.CheckFavorite -> checkIsFavorite(state.value.productId)
            is DetailEvent.AddProductToCart -> addToCartProduct(event.product)
            is DetailEvent.RemoveProductFromCart -> removeProductFromCart(event.product)
            is DetailEvent.DecreaseCartProduct -> decreaseCartProduct(event.product)
            is DetailEvent.IncreaseCartProduct -> increaseCartProduct(event.product)
        }
    }
}
