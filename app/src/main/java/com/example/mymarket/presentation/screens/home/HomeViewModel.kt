package com.example.mymarket.presentation.screens.home

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
class HomeViewModel @Inject constructor(
    private val getProductUSeCase: GetProductsUseCase
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state : State<HomeState> = _state

    private var job : Job? = null

    init {
        getProducts()
    }

    private fun getProducts() {
        job?.cancel()
        job = getProductUSeCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = HomeState(products = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = HomeState(error = it.message ?: "")
                }

                is Resource.Loading -> {
                    _state.value = HomeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SearchProduct -> {
                getProducts()
            }
        }
    }
}