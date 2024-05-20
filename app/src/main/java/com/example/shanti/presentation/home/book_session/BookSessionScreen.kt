package com.example.shanti.presentation.home.book_session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSessionScreen() {

    Text(text = "Book Visit Screen")

    val datePickerState = rememberDatePickerState(initialDisplayedMonthMillis = null)

    Column(
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "${datePickerState.selectedDateMillis}? : niente"
        )

    }


}