package com.example.shanti.navigation.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shanti.navigation.graph.Graph
import com.example.shanti.presentation.onboard.OnboardScreen

@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = startDestination
    ) {
        composable(
            route = Graph.ONBOARD
        ) {
            OnboardScreen(
                rootNavHostController = navHostController,
            )
        }
    }
}