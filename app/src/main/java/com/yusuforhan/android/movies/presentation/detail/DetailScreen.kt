package com.yusuforhan.android.movies.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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
            CustomIconButton(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(14.dp)
                    .height(56.dp),
                iconTint = Color.Red,
                onButtonClicked = {

                },
                icon = Icons.Default.PlayArrow
            )

        } else if (state.isError != null) {
            Text(text = state.isError)

        }

    }

}

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    iconTint: Color,
    onButtonClicked: () -> Unit,
    icon: ImageVector,
    iconSize: Dp = 24.dp,
    shape: Shape = MaterialTheme.shapes.medium,
    elevation: Dp = 5.dp,
    paddingValue: PaddingValues = PaddingValues(0.dp),
) {
    Box(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape)
            .clip(shape)
            .background(backgroundColor)
            .clickable {
                onButtonClicked()
            }
            .padding(paddingValues = paddingValue)
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
            imageVector = icon,
            contentDescription = "icon",
            tint = iconTint
        )
    }
}
