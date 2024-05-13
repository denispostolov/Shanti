package com.example.shanti.navigation.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shanti.navigation.graph.Graph
import com.example.shanti.presentation.home.HomeScreen
import com.example.shanti.presentation.home.HomeScreenContent
import com.example.shanti.presentation.onboard.OnboardScreen
import com.example.shanti.presentation.signin.GoogleAuthUIClient
import com.example.shanti.presentation.signin.SignInScreenContent
import com.example.shanti.session.SessionManager


@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String,
    googleAuthUIClient: GoogleAuthUIClient,
    sessionManager: SessionManager
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
                sessionManager = sessionManager
            )
        }
        composable(
            route = Graph.SIGNIN
        ) {
            SignInScreenContent(rootNavHostController = navHostController, googleAuthUIClient = googleAuthUIClient, sessionManager = sessionManager)
        }
        composable(
            route = Graph.HOME
        ) {
            //HomeScreen(rootNavHostController = navHostController, googleAuthUIClient = googleAuthUIClient, sessionManager = sessionManager)
            HomeScreenContent(
                rootNavHostController = navHostController,
                googleAuthUIClient = googleAuthUIClient,
                sessionManager = sessionManager,
            )
        }
    }
}