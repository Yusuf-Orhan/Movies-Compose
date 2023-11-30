package com.yusuforhan.android.movies.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.yusuforhan.android.movies.R
import com.yusuforhan.android.movies.data.model.MovieDetail
import com.yusuforhan.android.movies.presentation.components.ECProgressBar

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
    modifier: Modifier = Modifier,
    state: DetailUiState,
) {
    ECProgressBar(showState = state.isLoading)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (state.movie != null) {
            val movie = state.movie
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .height(500.dp),
                model = movie.Poster,
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
            DetailBody(movieDetail = movie)

        } else if (state.isError != null) {
            Text(text = state.isError)
        }
    }
}

@Composable
fun DetailBody(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = movieDetail.Title, style = TextStyle(color = Color.White, fontSize = 20.sp))
        Text(
            text = "Release Date : ${movieDetail.Title}",
            style = TextStyle(color = Color.White, fontSize = 20.sp)
        )
        StarIcon(rating = "5.4")
    }
}

@Composable
fun StarIcon(
    modifier: Modifier = Modifier,
    rating: String
) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.ic_star_24),
            contentDescription = null,
            tint = Color.Yellow
        )
        Text(text = rating, style = TextStyle(color = Color.White))
    }


}