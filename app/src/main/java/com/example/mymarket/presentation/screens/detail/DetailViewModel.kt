package com.example.mymarket.presentation.screens.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymarket.domain.usecase.getproducts.GetProductsUseCase
import com.example.mymarket.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductUSeCase: GetProductsUseCase
): ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state : State<DetailState> = _state

    private var job : Job? = null

    init {
        getProducts()
    }

    private fun getProducts() {
        job = getProductUSeCase.executeGetProducts().onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = DetailState(products = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = DetailState(error = it.message ?: "")
                }

                is Resource.Loading -> {
                    _state.value = DetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.SearchProduct -> {
                getProducts()
            }
        }
    }
}