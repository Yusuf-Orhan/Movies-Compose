package com.yusuforhan.android.movies.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuforhan.android.movies.common.Constants.DEFAULT_SEARCH
import com.yusuforhan.android.movies.common.Resource
import com.yusuforhan.android.movies.data.model.Search
import com.yusuforhan.android.movies.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MoviesRepository
)  : ViewModel(){
    val state = mutableStateOf(HomeState(isLoading = true))


    fun onEvent(event : HomeUiEvents){
        when(event){
            is HomeUiEvents.TryAgainClicked -> {
                state.value = state.value.copy(isLoading = true)
                getMovies(DEFAULT_SEARCH)
            }
            is HomeUiEvents.OnSearch -> {
                state.value = state.value.copy(isLoading = true)
                getMovies(event.searchString)
            }
        }
    }
    init {
        getMovies(DEFAULT_SEARCH)
    }

    private fun getMovies(searchString: String?) = viewModelScope.launch {
        when (val result = repository.getMovies(searchString ?: DEFAULT_SEARCH)) {
            is Resource.Success -> {
                state.value = state.value.copy(isLoading = false, movies = result.data.search)
                println("Success")
            }

            is Resource.Error -> {
                state.value = state.value.copy(isLoading = false, error = result.msg)
                println("Error")
            }
        }
    }
}
data class HomeState(
    val isLoading: Boolean = false,
    val movies: List<Search>? = emptyList(),
    val error: String? = null
)

sealed class HomeUiEvents {
    data object TryAgainClicked : HomeUiEvents()
    data class OnSearch(val searchString: String) : HomeUiEvents()
}