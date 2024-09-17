package com.example.mymarket.presentation.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymarket.data.datasource.locale.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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