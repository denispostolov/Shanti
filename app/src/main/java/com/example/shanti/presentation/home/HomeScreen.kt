package com.example.shanti.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants
import com.example.shanti.components.EditSessionSheet
import com.example.shanti.components.SimpleDeleteSessionDialog
import com.example.shanti.components.SimpleLottieFiles
import com.example.shanti.components.SimpleSessionCard
import com.example.shanti.data.entity.SessionEntity
import com.example.shanti.presentation.home.book_session.BookSessionViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    bookSessionViewModel: BookSessionViewModel
) {
    val scope = rememberCoroutineScope()
    val sessions = homeScreenViewModel.sessions.collectAsState(null)
    val sheetState = rememberModalBottomSheetState()
    var isEditSessionSheetOpen by remember { mutableStateOf(false) }
    var selectedSession by remember { mutableStateOf<SessionEntity?>(null) }

    if (isEditSessionSheetOpen && selectedSession != null) {
        EditSessionSheet(
            sheetState = sheetState,
            isOpen = isEditSessionSheetOpen,
            session = selectedSession!!,
            onDismissRequest = { isEditSessionSheetOpen = false },
            onSessionUpdate = { updatedSession ->
                bookSessionViewModel.insertSession(updatedSession)
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        isEditSessionSheetOpen = false
                    }
                }
            },
            onSessionDelete = {
                //bookSessionViewModel.insertSession(session)
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        isEditSessionSheetOpen = false
                        homeScreenViewModel.openSessionDeleteDialog = true
                    }
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (sessions.value != null && sessions.value!!.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(sessions.value!!) { sessionEntity ->
                    SimpleSessionCard(
                        session = sessionEntity,
                        onClick = { session ->
                            selectedSession = session
                            isEditSessionSheetOpen= true
                        }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleLottieFiles(
                    urlLottieFiles = "https://assets9.lottiefiles.com/packages/lf20_qpLa3wzbPB.json",
                    iterations = LottieConstants.IterateForever
                )
            }
        }
    }

    if (homeScreenViewModel.openSessionDeleteDialog && selectedSession != null) {
        SimpleDeleteSessionDialog(viewModel = homeScreenViewModel) {
            homeScreenViewModel.openSessionDeleteDialog = false
            homeScreenViewModel.deleteSession(selectedSession!!)
        }
    }
}