package com.yusuforhan.android.movies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuforhan.android.movies.common.Resource
import com.yusuforhan.android.movies.data.model.Movies
import com.yusuforhan.android.movies.data.model.Search
import com.yusuforhan.android.movies.domain.repository.MoviesRepository
import com.yusuforhan.android.movies.presentation.base.BaseViewModel
import com.yusuforhan.android.movies.presentation.base.Effect
import com.yusuforhan.android.movies.presentation.base.Event
import com.yusuforhan.android.movies.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MoviesRepository
) : BaseViewModel<HomeUiState, HomeEffects, HomeEvents>() {
    override fun setInitialState(): HomeUiState = HomeUiState(true)

    override fun handleEvent(event: HomeEvents) {

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
                setState(HomeUiState(false))
                setEffect(HomeEffects.ShowError(result.msg))
            }
        }
    }

}

data class HomeUiState(
    val isLoading: Boolean = false,
    val movies: List<Search> = emptyList()
) : State

sealed interface HomeEffects : Effect {
    data class ShowError(val msg: String) : HomeEffects
}

sealed interface HomeEvents : Event {

}


