package com.example.shanti.presentation.home

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.shanti.session.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    rootNavHostController: NavHostController,
    navHostController: NavHostController = rememberNavController(),
    sessionManager: SessionManager,
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
        navHostController = navHostController){
    }


}