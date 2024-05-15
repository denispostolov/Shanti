package com.example.shanti.presentation.home

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shanti.components.SimpleModalNavigationDrawer
import com.example.shanti.navigation.graph.Graph
import com.example.shanti.presentation.signin.GoogleAuthUIClient
import com.example.shanti.session.SessionManager
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.shanti.navigation.screen.HomeScreen
import kotlinx.coroutines.launch
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import com.example.shanti.navigation.host.HomeNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    rootNavHostController: NavHostController,
    navHostController: NavHostController = rememberNavController(),
    sessionManager: SessionManager,
    googleAuthUIClient: GoogleAuthUIClient,
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (it.resultCode == Activity.RESULT_OK) {
                sessionManager.setUserUnLogged()

            }
        }
    )

    SimpleModalNavigationDrawer(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    content = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                                label = { Text("Home") },
                                alwaysShowLabel = true,
                                selected = navBackStackEntry?.destination?.route == HomeScreen.Home.route,
                                onClick = {
                                    navHostController.navigate(HomeScreen.Home.route)
                                }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.DateRange, contentDescription = "Book Session") },
                                label = { Text("Book Session") },
                                alwaysShowLabel = true,
                                selected = navBackStackEntry?.destination?.route == HomeScreen.BookSession.route,
                                onClick = {
                                    navHostController.navigate(HomeScreen.BookSession.route)
                                }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.Face, contentDescription = "Breath Session") },
                                label = { Text("Breath") },
                                alwaysShowLabel = true,
                                selected = navBackStackEntry?.destination?.route == HomeScreen.BreathSession.route,
                                onClick = {
                                    navHostController.navigate(HomeScreen.BreathSession.route)
                                }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                                label = { Text("Profile") },
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
                googleAuthUIClient = googleAuthUIClient
            )
        }
    }


}