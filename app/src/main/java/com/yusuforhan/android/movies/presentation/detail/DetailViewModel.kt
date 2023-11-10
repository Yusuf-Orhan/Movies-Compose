package com.yusuforhan.android.movies.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yusuforhan.android.movies.common.Resource
import com.yusuforhan.android.movies.data.model.MovieDetail
import com.yusuforhan.android.movies.data.model.Search
import com.yusuforhan.android.movies.domain.repository.MoviesRepository
import com.yusuforhan.android.movies.presentation.base.BaseViewModel
import com.yusuforhan.android.movies.presentation.base.Event
import com.yusuforhan.android.movies.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(
    private val repository: MoviesRepository,
    setStateHandle: SavedStateHandle
) : BaseViewModel<DetailUiState, DetailEvents>() {


    val state = mutableStateOf(DetailUiState(isLoading = true))

    override fun setInitialState(): DetailUiState {
        return DetailUiState(true)
    }

    override fun handleEvent(event: DetailEvents) {
        when(event){
            is  DetailEvents.AddFavorite -> {

            }
        }
    }
    init {
        setStateHandle.get<String>("imdbId")?.let {
            println(it)
            getMovieDetail(it)
        }

    }
    private fun getMovieDetail(id : String) = viewModelScope.launch {
        when(val result = repository.getMovieDetail(id)){
            is Resource.Success -> {
                state.value = state.value.copy(isFavorite = false,isLoading = false, movie = result.data)
            }
            is Resource.Error -> {
                state.value = state.value.copy(isFavorite = false,isLoading = false,movie = null,isError = result.msg)
            }
        }
    }
}

data class DetailUiState(
    val isFavorite : Boolean = false,
    val isLoading: Boolean = false,
    val movie: MovieDetail? = null,
    val isError : String? = null
) : State


sealed class DetailEvents : Event {
    data object AddFavorite : DetailEvents()
}