package com.example.shanti

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.shanti.common.Constants
import com.example.shanti.data.database.SessionDatabase
import com.example.shanti.data.database.TrainerDatabase
import com.example.shanti.navigation.graph.Graph
import com.example.shanti.navigation.host.RootNavHost
import com.example.shanti.presentation.home.HomeScreenViewModel
import com.example.shanti.presentation.home.book_session.BookSessionViewModel
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
            val sessionsDatabase = SessionDatabase.getDatabase(this)
            val trainersDatabase = TrainerDatabase.getDatabase(this)
            ShantiTheme {
                val homeScreenViewModel: HomeScreenViewModel by viewModels {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
                                @Suppress("UNCHECKED_CAST")
                                return HomeScreenViewModel(sessionsDatabase!!) as T
                            }
                            throw IllegalArgumentException("Unknown ViewModel class")
                        }
                    }
                }
                val bookSessionViewModel: BookSessionViewModel by viewModels {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            if (modelClass.isAssignableFrom(BookSessionViewModel::class.java)) {
                                @Suppress("UNCHECKED_CAST")
                                return BookSessionViewModel(trainersDatabase!!, sessionsDatabase!!, homeScreenViewModel) as T
                            }
                            throw IllegalArgumentException("Unknown ViewModel class")
                        }
                    }
                }

                // Code to initialise SessionDatabase the first time
                //homeScreenViewModel.init()
                // Code to initialise TrainerDatabase the first time
                //bookSessionViewModel.init()

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
                        sessionManager = sessionManager,
                        homeScreenViewModel = homeScreenViewModel,
                        bookSessionViewModel = bookSessionViewModel
                    )
                }
            }
        }
    }
}
