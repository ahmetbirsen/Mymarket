package com.example.mymarket.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.properties.Delegates

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseAction>(initialState: ViewState) :
    ViewModel() {
    private val mutex = Mutex()

    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiState = _uiStateFlow.asStateFlow()

    protected var state by Delegates.observable(initialState) { _, old, new ->
        viewModelScope.launch {
            _uiStateFlow.emit(new)
        }
    }

    fun sendAction(viewAction: ViewAction) {
        onReduceState(viewAction)?.let {
            state = it
        }
    }

    fun updateState(viewState: ViewState) {
        viewModelScope.launch(Dispatchers.IO) {
            mutex.withLock {
                state = viewState
            }
        }
    }

    open fun getString(resId: Int): String? = null
    protected abstract fun onReduceState(viewAction: ViewAction): ViewState?
}