package com.yusuforhan.android.movies.presentation.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val detailRoute = "detail_route"
fun NavGraphBuilder.detailScreen(
    navigateToBack : () -> Unit
){
    composable(
        route = detailRoute.plus("/{imdbId}"),
    ){
        DetailRoute(navigateBack = navigateToBack)
    }
}


fun NavController.navigateToDetail(
    imdbId : String,
    navOptions : NavOptions? = null
){
    navigate(
        route = detailRoute.plus("/$imdbId"),
        navOptions = navOptions
    )
}