package com.example.mymarket.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymarket.R
import com.example.mymarket.core.util.BooleanExt.safeGet
import com.example.mymarket.core.util.StringExt.empty
import com.example.mymarket.data.datasource.locale.datastore.DataStoreManager
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.model.FilterCriteria
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.domain.usecase.MarketUseCases
import com.example.mymarket.domain.util.Resource
import com.example.mymarket.domain.util.ResourceManager
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
    private val dataStoreManager: DataStoreManager,
    private val resourceManager: ResourceManager
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private var job: Job? = null
    fun getString(resId: Int) = resourceManager.getString(resId)

    init {
        getProducts()
    }

    private fun getProducts() {
        job?.cancel()
        job = marketUseCases.getProductsUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    dataStoreManager.updateCartCount(
                        marketUseCases.getCartProductCountUseCase().first()
                    )
                    val brands = it.data?.map { product -> product.brand ?: String.empty }?.distinct()
                    val models = it.data?.map { product -> product.model ?: String.empty}?.distinct()
                    _state.value = _state.value.copy(
                        products = it.data ?: emptyList(),
                        isLoading = false,
                        brands = brands ?: emptyList(),
                        models = models ?: emptyList(),
                        error = String.empty
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: String.empty)
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, error = String.empty)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addToCartProduct(product: ProductDto) {
        viewModelScope.launch {
            marketUseCases.addProductToCartUseCase(
                Product(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    createdAt = product.createdAt,
                    brand = product.brand,
                    model = product.model,
                    image = product.image,
                    price = product.price,
                )
            ).onEach {
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

    private fun searchFilter(query: String) {
        viewModelScope.launch {
            val filteredProductResponseList = state.value.products?.filter {
                it.model?.contains(query, ignoreCase = true).safeGet() ||
                        it.brand?.contains(query, ignoreCase = true).safeGet() ||
                        it.name?.contains(query, ignoreCase = true).safeGet() ||
                        it.description?.contains(query, ignoreCase = true).safeGet()
            }
            _state.value = _state.value.copy(isLoading = true, error = "")
            if (filteredProductResponseList.isNullOrEmpty()) {
                _state.value = _state.value.copy(
                    error = "No products found",
                    isLoading = false,
                    products = emptyList()
                )
            } else {
                _state.value = _state.value.copy(
                    error = "",
                    isLoading = false,
                    searchQuery = query,
                    products = filteredProductResponseList
                )
            }
        }
    }

    private fun getBrands() {
        viewModelScope.launch {
            val brands = state.value.brands.map { brand -> brand }
            _state.value = _state.value.copy(
                brands = brands,
                filteredBrands = brands
            )
        }
    }

    private fun getModels() {
        viewModelScope.launch {
            val models = state.value.models.map { model -> model }
            _state.value = _state.value.copy(
                models = models,
                filteredModels = models
            )
        }
    }

    private fun searchFilterBrand(query: String) {
        viewModelScope.launch {
           val brandFilterList = state.value.brands.filter {
                it.contains(query, ignoreCase = true)
            }
            _state.value = _state.value.copy(error = String.empty)
            if (brandFilterList.isEmpty()) {
                _state.value = _state.value.copy(
                    error = getString(R.string.no_brands_found),
                    isLoading = false,
                )
            } else {
                _state.value = _state.value.copy(
                    error = String.empty,
                    isLoading = false,
                    filteredBrands = brandFilterList
                )
            }
        }
    }

    private fun searchFilterModel(query: String) {
        viewModelScope.launch {
            val modelFilterList = state.value.models.filter {
                it.contains(query, ignoreCase = true)
            }
            _state.value = _state.value.copy(error = String.empty)
            if (modelFilterList.isEmpty()) {
                _state.value = _state.value.copy(
                    error = getString(R.string.no_models_found),
                    isLoading = false,
                )
            } else {
                _state.value = _state.value.copy(
                    error = String.empty,
                    isLoading = false,
                    filteredModels = modelFilterList
                )
            }
        }
    }

    private fun getFilteredProducts(filterCriteria: FilterCriteria){

    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.IncreaseCartProduct -> {
                increaseCartProduct(product = event.product)
            }

            HomeEvent.GetProducts -> {
                getProducts()
            }

            is HomeEvent.AddToCart -> {
                addToCartProduct(product = event.product)
            }

            is HomeEvent.DecreaseCartProduct -> {
                decreaseCartProduct(product = event.product)
            }

            is HomeEvent.DeleteFavorite -> {
                deleteFavoriteProduct(productId = event.product.id)
            }

            is HomeEvent.InsertFavorite -> {
                insertFavoriteProduct(product = event.product)
            }

            is HomeEvent.UpdateFilterModal -> {
                _state.value = _state.value.copy(
                    filterBottomModal = event.isVisible
                )
            }

            is HomeEvent.SearchFilter -> {
                searchFilter(event.query)
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }

            is HomeEvent.GetBrands -> getBrands()
            is HomeEvent.GetModels -> getModels()
            is HomeEvent.FilterByBrand -> searchFilterBrand(query = event.brand)
            is HomeEvent.FilterByModel -> searchFilterModel(query = event.model)
        }
    }
}