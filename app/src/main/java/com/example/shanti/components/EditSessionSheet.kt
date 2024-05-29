package com.example.shanti.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.shanti.common.Constants
import com.example.shanti.common.defineStatus
import com.example.shanti.common.localDateToDate
import com.example.shanti.common.showDatePickerDialog
import com.example.shanti.common.showTimePickerDialog
import com.example.shanti.data.entity.SessionEntity
import com.example.shanti.domain.model.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSessionSheet(
    sheetState: SheetState,
    isOpen: Boolean,
    bottomSheetTitle: String = "Edit Session",
    session: SessionEntity,
    onSessionUpdate: (SessionEntity) -> Unit,
    onSessionDelete: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var pickedDate by remember { mutableStateOf(session.dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) }
    var pickedTime by remember { mutableStateOf(LocalTime.parse(session.time)) }
    var selectedStatus by remember { mutableStateOf(session.status) }
    var isSessionStatusDropdownExpanded by remember { mutableStateOf(false) }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd MMM yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("HH:mm")
                .format(pickedTime)
        }
    }


    if(isOpen){
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(text = bottomSheetTitle)
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                }
            }
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Date Picker
                Text(text = "Date")
                Text(
                    text = formattedDate,
                    modifier = Modifier.clickable {
                        showDatePickerDialog(context, pickedDate) { year, month, day ->
                            pickedDate = LocalDate.of(year, month + 1, day)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Time Picker
                Text(text = "Time")
                Text(
                    text = formattedTime,
                    modifier = Modifier.clickable {
                        showTimePickerDialog(context, pickedTime) { hour, minute ->
                            pickedTime = LocalTime.of(hour, minute)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Session Status
                Text(text = "Session Status")
                DropdownMenu(
                    expanded = isSessionStatusDropdownExpanded,
                    onDismissRequest = { isSessionStatusDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Status.entries.forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.toString()) },
                            onClick = {
                                selectedStatus = status
                                isSessionStatusDropdownExpanded = false
                            }
                        )
                    }
                }

                Text(selectedStatus.toString(), modifier = Modifier.clickable { isSessionStatusDropdownExpanded = true })

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Delete Button
                    Button(
                        onClick = {
                            scope.launch {
                                onSessionDelete()
                                onDismissRequest()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,  // Background color
                            contentColor = Color.White  // Text color
                        )
                    ) {
                        Text("Delete Session")
                    }

                    // Update Button
                    Button(onClick = {
                        scope.launch {
                            val updatedSession = session.copy(
                                dateTime = localDateToDate(pickedDate),
                                time = formattedTime,
                                practiseType = session.practiseType,
                                trainerName = session.trainerName,
                                trainerSurname = session.trainerSurname,
                                status = if(selectedStatus != Status.CANCELED) defineStatus(localDateToDate(pickedDate),pickedTime) else Status.CANCELED,
                                urlMeet = Constants.URL_GOOGLE_MEET
                            )
                            onSessionUpdate(updatedSession)
                            onDismissRequest()

                            // Show Toast notification
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Session updated successfully!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }) {
                        Text("Update Session")
                    }

                }

            }
        }
    }

}
