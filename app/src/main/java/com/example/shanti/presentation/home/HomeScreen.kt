package com.example.shanti.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shanti.components.SimpleModalNavigationDrawer
import com.example.shanti.navigation.graph.Graph
import com.example.shanti.presentation.signin.GoogleAuthUIClient
import com.example.shanti.session.SessionManager
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    rootNavHostController: NavHostController,
    googleAuthUIClient: GoogleAuthUIClient,
    sessionManager: SessionManager
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val lifecycleScope = lifecycleOwner.lifecycleScope

    Column(
        modifier = Modifier.padding(32.dp)
    ){
        Text(text = "Home screen")
        Button(
            onClick = {
                lifecycleScope.launch {
                    googleAuthUIClient.signOut()
                }
                sessionManager.setUserUnLogged()
                rootNavHostController.navigate(Graph.SIGNIN)
            }
        ) {
            Text(text = "Sign Out")
        }
    }
}