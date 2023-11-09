package com.yusuforhan.android.movies.presentation.base

/*import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : State, EVENT : Event> : ViewModel() {


    private val initialState: STATE by lazy { setInitialState() }
    abstract fun setInitialState(): STATE

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()


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

    fun setEvent(event: EVENT) {
        viewModelScope.launch { _event.emit(event) }
    }
}

interface State
interface Event

 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : State, EVENT : Event> : ViewModel() {

    private val initialState: STATE by lazy { setInitialState() }
    abstract fun setInitialState(): STATE

    private val _state: MutableState<STATE> = mutableStateOf(initialState)
    val basestate: androidx.compose.runtime.State<STATE> get() = _state

    private val _event: MutableState<EVENT?> = mutableStateOf(null)
    val event: MutableState<EVENT?> get() = _event

    abstract fun handleEvent(event: EVENT)

    private fun subscribeToEvent() {
        viewModelScope.launch {
            _event.value?.let {
                handleEvent(it)
            }
        }
    }

    init {
        subscribeToEvent()
    }

    fun getCurrentState() = basestate.value

    fun setState(newState: STATE) {
        _state.value = newState
    }

    fun setEvent(newEvent: EVENT) {
        _event.value = newEvent
    }
}

interface State
interface Event
