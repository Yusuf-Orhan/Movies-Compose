package com.yusuforhan.android.movies.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.yusuforhan.android.movies.R
import com.yusuforhan.android.movies.data.model.Search
import com.yusuforhan.android.movies.presentation.components.ECProgressBar

@Composable
fun HomeRoute(
    navigateToDetail: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        Modifier,
        state,
        onItemClick = navigateToDetail,
        handleEvents = viewModel::setEvent
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onItemClick: (String) -> Unit,
    handleEvents : (HomeEvents) -> Unit,
) {
    Column {

        ECProgressBar(state.isLoading)
        if (state.movies.isNotEmpty()) {
            Spacer(modifier = modifier.padding(16.dp))
            MovieList(movies = state.movies, onItemClick = onItemClick)
        }else if (state.error != null){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ErrorScreen(errorMsg = state.error, tryButtonClick = {handleEvents(HomeEvents.TryAgainClicked)})
            }
        }
    }
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: List<Search>,
    onItemClick: (String) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        items(movies) { movie ->
            MovieItem(imageUrl = movie.poster, modifier = modifier) {
                onItemClick(movie.imdbID)
            }
        }
    }
}

@Composable
fun MovieItem(
    imageUrl: String,
    modifier: Modifier,
    onclick: () -> Unit
) {

    AsyncImage(
        model =  imageUrl,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onclick() },
        contentDescription = "Movie banner",
        filterQuality = FilterQuality.High
    )

}
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorMsg: String,
    tryButtonClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier,
            text = errorMsg,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        )
        Spacer(modifier = modifier.padding(16.dp))
        ElevatedButton(onClick = {
            tryButtonClick()
        }) {
            Text(text = "Try Again!")
        }
    }
}