package com.example.shanti.presentation.home.book_session

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.shanti.components.PractiseTypeListSheet
import com.example.shanti.components.TrainerListSheet
import com.example.shanti.data.mappers.toTrainer
import com.example.shanti.domain.model.Gender
import com.example.shanti.domain.model.PractiseType
import com.example.shanti.domain.model.Trainer
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSessionScreen(viewModel: BookSessionViewModel) {

    val scope = rememberCoroutineScope()

    var pickedDate by remember { mutableStateOf(LocalDate.now())}
    var pickedTime by remember { mutableStateOf(LocalTime.now())}

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    val sheetState = rememberModalBottomSheetState()
    var isPractiseTypeSheetOpen by remember { mutableStateOf(false) }
    var isTrainerSheetOpen by remember { mutableStateOf(false) }

    var selectedPractiseType by remember { mutableStateOf(PractiseType.YOGA) }
    var selectedTrainer by remember { mutableStateOf("") }

    val practiseTypes = listOf(PractiseType.YOGA,PractiseType.MEDITATION, PractiseType.BOTH)
    val trainers by viewModel.trainersByPractiseType.collectAsState()


    val context = LocalContext.current

    PractiseTypeListSheet(
        sheetState = sheetState,
        isOpen = isPractiseTypeSheetOpen,
        practiseTypes = practiseTypes,
        onDismissRequest = {isPractiseTypeSheetOpen = false},
        onPractiseTypeClicked = { practiseType ->
            // Get all the trainers specific for practiseType
            viewModel.selectPractiseType(practiseType)
            // Reset selected trainer
            selectedTrainer = ""
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if(!sheetState.isVisible){
                    isPractiseTypeSheetOpen = false
                    selectedPractiseType = practiseType
                }
            }
        }
    )

    TrainerListSheet(
        sheetState = sheetState,
        isOpen = isTrainerSheetOpen,
        trainers = trainers.map { it.toTrainer() },
        onDismissRequest = {isTrainerSheetOpen = false},
        onTrainerClicked = { trainer ->
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if(!sheetState.isVisible){
                    isTrainerSheetOpen = false
                    selectedTrainer = trainer.fullName()
                }
            }
        }
    )

    Scaffold(
        topBar = {
            BookSessionTopBar(
                onDeleteButtonClick = {}
            )
        }
    ) { contentPadding ->

            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(horizontal = 12.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showDatePickerDialog(context, pickedDate) { year, month, day ->
                                pickedDate = LocalDate.of(year, month + 1, day)
                            }
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(onClick = {
                        showDatePickerDialog(context, pickedDate) { year, month, day ->
                            pickedDate = LocalDate.of(year, month + 1, day)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Select Time",
                    style = MaterialTheme.typography.bodySmall
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showTimePickerDialog(context, pickedTime) { hour, minute ->
                                pickedTime = LocalTime.of(hour, minute)
                            }
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formattedTime,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(onClick = {
                        showTimePickerDialog(context, pickedTime) { hour, minute ->
                            pickedTime = LocalTime.of(hour, minute)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Select Time"
                        )
                    }
                }

                Text(
                    text = "Select Type of Practise",
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isPractiseTypeSheetOpen = true },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedPractiseType.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(onClick = { isPractiseTypeSheetOpen = true }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Select Practise Type"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Select Trainer",
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isTrainerSheetOpen = true },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedTrainer,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(onClick = { isTrainerSheetOpen = true }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Select Practise Type"
                        )
                    }
                }

            }
    }

}


private fun showDatePickerDialog(context: android.content.Context, initialDate: LocalDate, onDateSelected: (Int, Int, Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = initialDate.year
    val month = initialDate.monthValue - 1 // Month is 0-based in Calendar
    val day = initialDate.dayOfMonth

    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
        onDateSelected(selectedYear, selectedMonth, selectedDay)
    }, year, month, day).show()
}

private fun showTimePickerDialog(context: android.content.Context, initialTime: LocalTime, onTimeSelected: (Int, Int) -> Unit) {
    val hour = initialTime.hour
    val minute = initialTime.minute

    TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
        onTimeSelected(selectedHour, selectedMinute)
    }, hour, minute, true).show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookSessionTopBar(
    onDeleteButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Book Session",
                style = MaterialTheme.typography.headlineSmall
            )},
        actions = {
            IconButton(onClick = onDeleteButtonClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Your Progress in Booking Session"
                )
            }
        }
    )
}
