package com.example.shanti.presentation.home.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.shanti.R
import com.example.shanti.navigation.graph.Graph
import com.example.shanti.presentation.signin.GoogleAuthUIClient
import com.example.shanti.session.SessionManager
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(googleAuthUIClient: GoogleAuthUIClient, sessionManager: SessionManager, rootNavHostController: NavHostController) {
    val user = googleAuthUIClient.getSignedInUser()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(user?.profilePictureUrl),
            contentDescription = stringResource(R.string.profile_picture),
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = user?.username!!, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = user?.email!!, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    sessionManager.setUserUnLogged()
                    googleAuthUIClient.signOut()
                }
                rootNavHostController.navigate(Graph.SIGNIN){
                    popUpTo(Graph.HOME){inclusive = true}
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text(text = stringResource(R.string.sign_out), color = MaterialTheme.colorScheme.onError)
        }
    }
}
