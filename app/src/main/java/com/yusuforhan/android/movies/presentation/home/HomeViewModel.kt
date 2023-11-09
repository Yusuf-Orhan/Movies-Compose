package com.yusuforhan.android.movies.presentation.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.lifecycle.viewModelScope
import com.yusuforhan.android.movies.common.Resource
import com.yusuforhan.android.movies.data.model.Search
import com.yusuforhan.android.movies.domain.repository.MoviesRepository
import com.yusuforhan.android.movies.presentation.base.BaseViewModel
import com.yusuforhan.android.movies.presentation.base.Event
import com.yusuforhan.android.movies.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MoviesRepository
) : BaseViewModel<HomeUiState, HomeEvents>() {


    //val state = mutableStateOf(getCurrentState())
    val state = mutableStateOf(HomeUiState(isLoading = true))

    override fun setInitialState(): HomeUiState = HomeUiState(true)

    override fun handleEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.TryAgainClicked -> {
                setState(getCurrentState().copy(true))
                getMovies()
            }
        }
    }

    init {
        getMovies()
    }

    private fun getMovies() = viewModelScope.launch {
        when (val result = repository.getMovies()) {
            is Resource.Success -> {
                state.value = state.value.copy(isLoading = false, movies = result.data.search)
                //setState(HomeUiState(false, result.data.search))
                println("Success")
            }

            is Resource.Error -> {
                state.value = state.value.copy(isLoading = false, error = result.msg)
                //setState(HomeUiState(isLoading = false, error = result.msg))
                println("Error")
            }
        }
    }


}

data class HomeUiState(
    val isLoading: Boolean = false,
    val movies: List<Search>? = emptyList(),
    val error: String? = null
) : State

sealed class HomeEvents : Event {
    data object TryAgainClicked : HomeEvents()
}


