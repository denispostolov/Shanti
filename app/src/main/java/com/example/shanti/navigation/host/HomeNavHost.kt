package com.example.shanti.navigation.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shanti.navigation.graph.Graph
import com.example.shanti.navigation.screen.HomeScreen
import com.example.shanti.presentation.home.HomeScreen

@Composable
fun HomeNavHost(
    modifier: Modifier,
    rootNavHostController: NavHostController,
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        route = Graph.HOME,
        startDestination = startDestination
    ) {
        composable(
            route = HomeScreen.Home.route
        ) {
            //HomeScreen()
        }
    }

}