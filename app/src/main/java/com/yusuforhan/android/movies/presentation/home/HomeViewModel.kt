package com.yusuforhan.android.movies.presentation.home

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
    override fun setInitialState(): HomeUiState = HomeUiState(true)

    override fun handleEvent(event: HomeEvents) {
        when(event){
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
        when(val result = repository.getMovies()){
            is Resource.Success -> {
                setState(HomeUiState(false,result.data.search))
            }
            is Resource.Error -> {
                setState(HomeUiState(isLoading = false, error = result.msg))
            }
        }
    }

}

data class HomeUiState(
    val isLoading: Boolean = false,
    val movies: List<Search> = emptyList(),
    val error : String? = null
) : State

sealed class HomeEvents : Event {
    data object TryAgainClicked : HomeEvents()
}


