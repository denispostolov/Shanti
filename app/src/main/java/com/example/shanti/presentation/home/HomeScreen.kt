package com.example.shanti.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
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
    viewModel: HomeScreenViewModel
) {

    val sessions = viewModel.sessions.collectAsState(null)

    SimpleModalNavigationDrawer(
        drawerState = rememberDrawerState(DrawerValue.Closed),
        navHostController = rootNavHostController
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Sessions:")

            sessions.value?.let {
                sessions.value!!.forEach { session ->
                    Text(
                        text = "${session.trainerName} ${session.trainerSurname} - ${session.dateTime}",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Button(onClick = {
                // Example button action
            }) {
                Text(text = "Refresh")
            }
        }
    }
}