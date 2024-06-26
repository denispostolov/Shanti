package com.example.shanti.presentation.signin

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shanti.navigation.graph.Graph
import com.example.shanti.session.SessionManager
import kotlinx.coroutines.launch

@Composable
fun SignInScreenContent(
    rootNavHostController: NavHostController,
    googleAuthUIClient: GoogleAuthUIClient,
    sessionManager: SessionManager
) {

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycleScope = lifecycleOwner.lifecycleScope
    val context = LocalContext.current
    val viewModel: SignInViewModel = viewModel()
    val state = viewModel.state.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            lifecycleScope.launch {
                val signInResult = googleAuthUIClient.signInWithIntent(
                    intent = result.data ?: return@launch
                )
                viewModel.onSignInResult(signInResult)
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(state.value.isSignInSuccessful) {
        if (state.value.isSignInSuccessful) {
            // Show sign-in success toast
            Toast.makeText(
                context,
                "Hello ${googleAuthUIClient.getSignedInUser()?.username}!",
                Toast.LENGTH_LONG
            ).show()

            sessionManager.setUserLoggedIn()
            rootNavHostController.navigate(Graph.HOME)
            viewModel.resetState()
        }
    }

    SignInScreen(
        state = state.value,
        onSignInClick = {
            coroutineScope.launch {
                val signInIntentSender = googleAuthUIClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build()
                )
            }
        }
    )
}
