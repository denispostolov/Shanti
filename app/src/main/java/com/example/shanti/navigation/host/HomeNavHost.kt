package com.example.shanti.navigation.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shanti.navigation.graph.Graph
import com.example.shanti.navigation.screen.HomeScreen
import com.example.shanti.presentation.home.HomeScreen
import com.example.shanti.presentation.home.HomeScreenViewModel
import com.example.shanti.presentation.home.book_session.BookSessionScreen
import com.example.shanti.presentation.home.breath_session.BreathSessionScreen
import com.example.shanti.presentation.home.profile.ProfileScreen
import com.example.shanti.presentation.signin.GoogleAuthUIClient
import com.example.shanti.session.SessionManager

@Composable
fun HomeNavHost(
    modifier: Modifier,
    rootNavHostController: NavHostController,
    navHostController: NavHostController,
    startDestination: String,
    googleAuthUIClient: GoogleAuthUIClient,
    sessionManager: SessionManager,
    homeScreenViewModel: HomeScreenViewModel
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
            HomeScreen(rootNavHostController = rootNavHostController, viewModel = homeScreenViewModel)
        }
        composable(
            route = HomeScreen.BookSession.route
        ) {
            BookSessionScreen()
        }
        composable(
            route = HomeScreen.BreathSession.route
        ) {
            BreathSessionScreen()
        }
        composable(
            route = HomeScreen.Profile.route
        ) {
            ProfileScreen()
        }
    }

}