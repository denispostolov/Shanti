package com.example.shanti.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants
import com.example.shanti.components.SimpleLottieFiles
import com.example.shanti.components.SimpleSessionCard


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {

    val sessions = viewModel.sessions.collectAsState(null)

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
                    SimpleSessionCard(session = sessionEntity)
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
}