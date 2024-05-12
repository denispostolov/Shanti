package com.example.shanti

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.shanti.common.Constants
import com.example.shanti.navigation.graph.Graph
import com.example.shanti.navigation.host.RootNavHost
import com.example.shanti.presentation.signin.GoogleAuthUIClient
import com.example.shanti.session.AppSettings
import com.example.shanti.session.SessionManager
import com.example.shanti.ui.theme.ShantiTheme
import com.google.android.gms.auth.api.identity.Identity

class MainActivity : ComponentActivity() {

    private val googleAuthUIClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val appSettings by lazy {
        AppSettings(context = applicationContext)
    }

    private val sessionManager by lazy {
        SessionManager(applicationContext.getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShantiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    RootNavHost(
                        startDestination = if(sessionManager.isFirstAccess()) Graph.ONBOARD
                        else if (sessionManager.isUserLoggedIn()) Graph.HOME
                        else Graph.SIGNIN,
                        googleAuthUIClient = googleAuthUIClient,
                        sessionManager = sessionManager
                    )
                }
            }
        }
    }
}
