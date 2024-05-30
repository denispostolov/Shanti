package com.example.shanti.presentation.home

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shanti.presentation.signin.GoogleAuthUIClient
import com.example.shanti.session.SessionManager
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.shanti.navigation.screen.HomeScreen
import androidx.compose.material3.NavigationBar
import androidx.compose.ui.res.stringResource
import com.example.shanti.R
import com.example.shanti.navigation.host.HomeNavHost
import com.example.shanti.presentation.home.book_session.BookSessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    rootNavHostController: NavHostController,
    navHostController: NavHostController = rememberNavController(),
    sessionManager: SessionManager,
    googleAuthUIClient: GoogleAuthUIClient,
    homeScreenViewModel: HomeScreenViewModel,
    bookSessionViewModel: BookSessionViewModel
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (it.resultCode == Activity.RESULT_OK) {
                sessionManager.setUserUnLogged()

            }
        }
    )

    Scaffold(
        bottomBar = {
            BottomAppBar(
                content = {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.Home, contentDescription = stringResource(R.string.home)) },
                            label = { Text(stringResource(R.string.home)) },
                            alwaysShowLabel = true,
                            selected = navBackStackEntry?.destination?.route == HomeScreen.Home.route,
                            onClick = {
                                navHostController.navigate(HomeScreen.Home.route)
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.DateRange, contentDescription = stringResource(R.string.book_session)) },
                            label = { Text(stringResource(R.string.book_session)) },
                            alwaysShowLabel = true,
                            selected = navBackStackEntry?.destination?.route == HomeScreen.BookSession.route,
                            onClick = {
                                navHostController.navigate(HomeScreen.BookSession.route)
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.Face, contentDescription = stringResource(R.string.breath_session)) },
                            label = { Text(stringResource(R.string.breath)) },
                            alwaysShowLabel = true,
                            selected = navBackStackEntry?.destination?.route == HomeScreen.BreathSession.route,
                            onClick = {
                                navHostController.navigate(HomeScreen.BreathSession.route)
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = stringResource(R.string.profile)) },
                            label = { Text(stringResource(R.string.profile)) },
                            selected = navBackStackEntry?.destination?.route == HomeScreen.Profile.route,
                            onClick = {
                                navHostController.navigate(HomeScreen.Profile.route)
                            }
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        HomeNavHost(
            modifier = Modifier.padding(contentPadding),
            rootNavHostController = rootNavHostController,
            navHostController = navHostController,
            startDestination = HomeScreen.Home.route,
            sessionManager = sessionManager,
            googleAuthUIClient = googleAuthUIClient,
            homeScreenViewModel = homeScreenViewModel,
            bookSessionViewModel = bookSessionViewModel
        )
    }
}