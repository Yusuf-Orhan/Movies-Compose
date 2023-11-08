package com.yusuforhan.android.movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.yusuforhan.android.movies.presentation.home.homeScreen

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
       homeScreen(
           navigateToDetail = {

           }
       )
    }
}
