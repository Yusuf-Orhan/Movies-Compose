package com.yusuforhan.android.movies.presentation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val homeRoute = "home_route"

fun NavGraphBuilder.homeScreen(
    navigateToDetail: (Int) -> Unit
) {
    composable(
        route = homeRoute
    ) {
        HomeRoute(
            navigateToDetail = navigateToDetail
        )
    }

}