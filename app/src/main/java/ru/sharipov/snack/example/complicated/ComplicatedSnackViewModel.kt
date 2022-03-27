package ru.sharipov.snack.example.complicated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ComplicatedSnackViewModel: ViewModel() {

    private val state = MutableStateFlow(ComplicatedSnackState.LOADING)

    init {
        viewModelScope.launch {
            delay(1500)
            state.emit(ComplicatedSnackState.ERROR)
        }
    }

    fun observeState() : Flow<ComplicatedSnackState> {
        return state
    }

    fun handleError() {
        viewModelScope.launch {
            state.emit(ComplicatedSnackState.LOADING)
            delay(1500)
            state.emit(ComplicatedSnackState.SUCCESS)
        }
    }
}