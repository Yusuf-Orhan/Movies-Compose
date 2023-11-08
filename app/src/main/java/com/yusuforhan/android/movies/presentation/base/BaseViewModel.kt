package com.yusuforhan.android.movies.presentation.base

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.ui.text.font.emptyCacheFontFamilyResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : State, EFFECT : Effect, EVENT : Event> : ViewModel() {


    private val initialState: STATE by lazy { setInitialState() }
    abstract fun setInitialState(): STATE

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _effect: MutableSharedFlow<EFFECT> = MutableSharedFlow()
    val effect: SharedFlow<EFFECT> = _effect.asSharedFlow()

    private val _event: MutableSharedFlow<EVENT> = MutableSharedFlow()
    val event: SharedFlow<EVENT> = _event.asSharedFlow()

    abstract fun handleEvent(event: EVENT)

    private fun subscribeToEvent() {
        viewModelScope.launch {
            _event.collect {
                handleEvent(it)
            }
        }
    }
    init {
        subscribeToEvent()
    }
    fun getCurrentState() = state.value
    fun setState(state: STATE) {
        viewModelScope.launch { _state.emit(state) }
    }

    fun setEffect(effect: EFFECT) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    fun setEvent(event: EVENT) {
        viewModelScope.launch { _event.emit(event) }
    }
}

interface State
interface Effect
interface Event