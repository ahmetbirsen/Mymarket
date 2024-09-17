package com.example.mymarket.presentation.screens.favorites

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
class FavoritesViewModel @Inject constructor(
    private val marketUseCases: MarketUseCases,
    private val dataStoreManager: DataStoreManager
    ) : ViewModel() {

    private val _state = mutableStateOf(FavoritesState())
    val state: State<FavoritesState> = _state

    private var job: Job? = null

    init {
        getFavoriteProducts()
    }

    private fun getFavoriteProducts() {
        job?.cancel()
        job = marketUseCases.getFavoriteProductsUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    dataStoreManager.updateCartCount(marketUseCases.getCartProductCountUseCase().first())
                    _state.value = _state.value.copy(
                        favoriteProducts = it.data?.map {favorite ->
                            ProductDto(
                                id = favorite.id,
                                name = favorite.name,
                                price = favorite.price,
                                image = favorite.image,
                                description = favorite.description,
                                brand = favorite.brand,
                                model = favorite.model,
                                isFavorite = true,
                            )
                        } ?: emptyList(),
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
                        getFavoriteProducts()
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
                        getFavoriteProducts()
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

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            FavoritesEvent.GetFavoriteProducts -> {
                getFavoriteProducts()
            }

            is FavoritesEvent.DeleteFavorite -> {
                deleteFavoriteProduct(event.product.id)
            }
            is FavoritesEvent.InsertFavorite -> {
                insertFavoriteProduct(event.product)
            }
        }
    }
}