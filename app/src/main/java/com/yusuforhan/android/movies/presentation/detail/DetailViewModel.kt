package com.yusuforhan.android.movies.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.yusuforhan.android.movies.data.model.Search
import com.yusuforhan.android.movies.domain.repository.MoviesRepository
import com.yusuforhan.android.movies.presentation.base.BaseViewModel
import com.yusuforhan.android.movies.presentation.base.Event
import com.yusuforhan.android.movies.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(
    private val repository: MoviesRepository,
    val stateHandle: SavedStateHandle
) : BaseViewModel<DetailUiState, DetailEvents>() {
    override fun setInitialState(): DetailUiState {
        return DetailUiState(true)
    }

    override fun handleEvent(event: DetailEvents) {
        when(event){
            is  DetailEvents.AddFavorite -> {

            }
        }
    }
}

data class DetailUiState(
    val isLoading: Boolean = false,
    val movie: Search? = null
) : State


sealed class DetailEvents : Event {
    data object AddFavorite : DetailEvents()
}