package com.example.shanti.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.example.shanti.presentation.signin.GoogleAuthUIClient
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    rootNavHostController: NavHostController,
    googleAuthUIClient: GoogleAuthUIClient
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
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
                rootNavHostController.popBackStack()
            }
        ) {
            Text(text = "Sign Out")
        }
    }
}