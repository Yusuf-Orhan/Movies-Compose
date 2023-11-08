package com.yusuforhan.android.movies.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


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

