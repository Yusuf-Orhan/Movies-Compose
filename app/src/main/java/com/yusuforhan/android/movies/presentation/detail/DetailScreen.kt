package com.yusuforhan.android.movies.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun DetailRoute(
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state
    DetailScreen(
        state = state
    )
}


@Composable
fun DetailScreen(
    state: DetailUiState
) {

    Text(text = "Hello World")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (state.movie != null) {
            val movie = state.movie
            AsyncImage(model = movie.Poster, contentDescription = null)
        } else if (state.isError != null) {
            Text(text = state.isError)

        }

    }


}