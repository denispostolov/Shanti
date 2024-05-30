package com.example.shanti.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shanti.R
import com.example.shanti.presentation.home.book_session.BookSessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleClearBookingDialog(
    viewModel: BookSessionViewModel,
    clearBooking: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {}
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(100.dp, 100.dp)
                    ) {
                        SimpleLottieFiles(
                            urlLottieFiles = "https://lottie.host/8136094e-b54b-4886-b5ce-8bbc867da261/XBD0zkN9b5.json",
                            iterations = 1
                        )
                    }
                    Text(
                        text = stringResource(R.string.book_session_clear_dialog),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            viewModel.openClearBookingDialog = false
                        }
                    ) {
                        Text(stringResource(R.string.not_sure_text), color = Color.Red)
                    }
                    TextButton(
                        onClick = {
                            viewModel.openClearBookingDialog = false
                            clearBooking()
                        }
                    ) {
                        Text(stringResource(R.string.sure_text))
                    }
                }
            }
        }
    }
}