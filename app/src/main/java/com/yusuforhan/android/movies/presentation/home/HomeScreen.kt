package com.yusuforhan.android.movies.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.yusuforhan.android.movies.presentation.components.MovieItem
import com.yusuforhan.android.movies.presentation.components.SearchBar

@Composable
fun HomeRoute(
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    val events by viewModel.event.collectAsStateWithLifecycle(initialValue = null)
    HomeScreen(
        Modifier,
        state,
        effect,
        events,
        onItemClick = navigateToDetail
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    effect: HomeEffects?,
    events: HomeEvents?,
    onItemClick: (Int) -> Unit
) {
    Column {

        ECProgressBar(state.isLoading)
        if (state.movies.isNotEmpty()) {
            Spacer(modifier = modifier.padding(16.dp))

            MovieList(movies = state.movies, onItemClick = onItemClick)
        }

        when (effect) {
            is HomeEffects.ShowError -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ErrorScreen(errorMsg = effect.msg, tryButtonClick = {

                    })
                }
            }

            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView() {
    val searchString = remember {
        mutableStateOf("")
    }
    TextField(
        value = searchString.value,
        onValueChange = {
            searchString.value = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .padding(start = 24.dp, end = 24.dp, top = 20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = colorResource(id = R.color.edittextBgColor))
    )

}


@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: List<Search>,
    onItemClick: (Int) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        items(movies) { movie ->
            MovieItem(imageUrl = movie.poster, modifier = modifier) {
                onItemClick(movie.imdbID.toInt())
            }
        }
    }
}

/*@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Search,
    onItemClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable {
                movie.imdbID
                    .toIntOrNull()
                    ?.let { onItemClick(it) }
            }
    ) {
        Column(modifier = modifier) {
            AsyncImage(
                modifier = modifier
                    .height(210.dp)
                    .width(150.dp)
                    .fillMaxSize(), model = movie.poster, contentDescription = null
            )
        }
    }
}

 */

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