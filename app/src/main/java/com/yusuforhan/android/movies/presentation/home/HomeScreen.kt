package com.yusuforhan.android.movies.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.yusuforhan.android.movies.data.model.Search
import com.yusuforhan.android.movies.presentation.components.ECProgressBar

@Composable
fun HomeRoute(
    navigateToDetail: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state
    HomeScreen(
        Modifier,
        state,
        onItemClick = navigateToDetail,
        handleEvents = {
            viewModel.onEvent(it)
        }
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onItemClick: (String) -> Unit,
    handleEvents : (HomeUiEvents) -> Unit,
) {

    Column {
        Spacer(modifier = modifier.padding(10.dp))
        MovieSearchBar {searchString ->
            handleEvents(HomeUiEvents.OnSearch(searchString))
        }
        ECProgressBar(state.isLoading)
        if (state.movies != null) {
            state.movies.let { MovieList(movies = it, onItemClick = onItemClick) }

        }else if (state.error != null){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ErrorScreen(errorMsg = state.error, tryButtonClick = {handleEvents(HomeUiEvents.TryAgainClicked)})
            }
        }else{
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               Text(text = "Movies Not Found", style = TextStyle(color = Color.White, fontSize = 22.sp))
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchBar(
    modifier : Modifier = Modifier,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        TextField(
            value = text,
            keyboardActions = KeyboardActions(onDone = {
                onSearch(text)
            }),
            onValueChange = {
                text =it
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White)
            ,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }
        )
        if(isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}