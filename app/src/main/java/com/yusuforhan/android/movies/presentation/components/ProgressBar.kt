package com.yusuforhan.android.movies.presentation.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.yusuforhan.android.movies.R

@Composable
fun ECProgressBar(showState : Boolean) {
    if (showState){
        val progressValue = 1f
        val infiniteTransition = rememberInfiniteTransition(
            label = ""
        )

        val progressAnimationValue by infiniteTransition.animateFloat(
            initialValue = 0.0f,
            targetValue = progressValue,
            animationSpec = infiniteRepeatable(animation = tween(900)),
            label = ""
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.primaryColor))
                .alpha(0.5f),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = progressAnimationValue,
                modifier = Modifier
                    .wrapContentSize(),
                color = Color.Red
            )
        }
    }

}

@Composable
@Preview
fun PreviewProgress(){
    ECProgressBar(showState = true)
}