package com.example.shanti.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SimpleOnboardBottomSection(
    pagerState: PagerState,
    rootNavHostController: NavHostController,
) {

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = if (pagerState.currentPage != 2) Arrangement.SpaceBetween else Arrangement.Center
    ) {
        if (pagerState.currentPage == 2) {
            Button(
                onClick = {
                          //TODO: Sign in Login
                },
            ) {
                Text(text = "Get started!")
            }
        } else {
            TextButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = 2)
                    }
                }) {
                Text(text = "Skip")
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                    }
                }) {
                Text(text = "Next")
            }
        }
    }

}