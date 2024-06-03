package com.example.shanti.presentation.home.book_session

import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shanti.R
import com.example.shanti.common.Constants
import com.example.shanti.common.combineDateAndTime
import com.example.shanti.common.defineStatus
import com.example.shanti.common.getTrainerNameAndSurnameFromFullName
import com.example.shanti.common.localDateToDate
import com.example.shanti.common.showDatePickerDialog
import com.example.shanti.common.showTimePickerDialog
import com.example.shanti.components.AddToCalendarButton
import com.example.shanti.components.PractiseTypeListSheet
import com.example.shanti.components.SimpleClearBookingDialog
import com.example.shanti.components.TrainerListSheet
import com.example.shanti.data.entity.SessionEntity
import com.example.shanti.data.model.PractiseType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSessionScreen(viewModel: BookSessionViewModel) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

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
                .ofPattern("HH:mm")
                .format(pickedTime)
        }
    }

    val sheetState = rememberModalBottomSheetState()
    var isPractiseTypeSheetOpen by remember { mutableStateOf(false) }
    var isTrainerSheetOpen by remember { mutableStateOf(false) }

    var selectedPractiseType by remember { mutableStateOf(PractiseType.YOGA) }
    var selectedTrainer by remember { mutableStateOf("") }

    val practiseTypes = listOf(PractiseType.YOGA, PractiseType.MEDITATION, PractiseType.BOTH)
    val trainers by viewModel.trainersByPractiseType.collectAsState()


    val context = LocalContext.current

    PractiseTypeListSheet(
        sheetState = sheetState,
        isOpen = isPractiseTypeSheetOpen,
        practiseTypes = practiseTypes,
        onDismissRequest = {isPractiseTypeSheetOpen = false},
        onPractiseTypeClicked = { practiseType ->
            // Update the selectedPractiseType in order to get the updated list of trainers
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
        trainers = trainers,
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
                onDeleteButtonClick = {
                    viewModel.openClearBookingDialog = true
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { contentPadding ->

            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(horizontal = 24.dp)
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
                            contentDescription = stringResource(R.string.select_date)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.select_time),
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
                            contentDescription = stringResource(R.string.select_time)
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.select_type_of_practise),
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
                            contentDescription = stringResource(R.string.select_type_of_practise)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.select_trainer),
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
                            contentDescription = stringResource(R.string.select_trainer)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                // Getting the name and the surname from the selectedTrainer
                val (trainerName, trainerSurname) = getTrainerNameAndSurnameFromFullName(selectedTrainer)
                // Create the session
                val sessionEntity = SessionEntity(dateTime = localDateToDate(pickedDate),
                    time = formattedTime,
                    trainerName = trainerName,
                    trainerSurname = trainerSurname,
                    status = defineStatus(localDateToDate(pickedDate),pickedTime),
                    practiseType = selectedPractiseType,
                    urlMeet = Constants.URL_GOOGLE_MEET)

                Button(
                    onClick = {
                        scope.launch {
                            val snackbarResult = snackbarHostState.showSnackbar(
                                message = "Booking session...",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Short
                            )
                            if(snackbarResult == SnackbarResult.Dismissed) {
                                withContext(Dispatchers.Default){

                                    viewModel.insertSession(sessionEntity = sessionEntity)

                                    // Show Toast notification
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context,
                                            context.getString(R.string.session_booked_successfully), Toast.LENGTH_SHORT).show()
                                    }

                                }
                            }
                        }

                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(stringResource(R.string.book_session_button_text), style = MaterialTheme.typography.headlineMedium)
                }

                Spacer(modifier = Modifier.height(24.dp))

                AddToCalendarButton(
                    onClick = {
                        scope.launch {
                            val sessionStartCalendar = combineDateAndTime(sessionEntity.dateTime, sessionEntity.time)
                            val sessionEndCalendar = (sessionStartCalendar.clone() as Calendar).apply {
                                add(Calendar.HOUR, 1)  // Assuming the session lasts for 1 hour
                            }

                            // Add the event to the calendar
                            val calendarIntent = Intent(Intent.ACTION_INSERT).apply {
                                data = CalendarContract.Events.CONTENT_URI
                                putExtra(CalendarContract.Events.TITLE, "Yoga/Meditation Session")
                                putExtra(CalendarContract.Events.DESCRIPTION, "Session of ${sessionEntity.practiseType.toString().lowercase()} with ${trainerName} ${trainerSurname}")
                                putExtra(CalendarContract.Events.EVENT_LOCATION, "Online")
                                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, sessionStartCalendar.timeInMillis)
                                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, sessionEndCalendar.timeInMillis) // Assuming the session lasts for 1 hour
                                putExtra(CalendarContract.Events.EVENT_TIMEZONE, sessionStartCalendar.timeZone.id)
                            }
                            context.startActivity(calendarIntent)
                        }

                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

            }

        if (viewModel.openClearBookingDialog) {
            SimpleClearBookingDialog(viewModel = viewModel) {
                viewModel.openClearBookingDialog = false
                pickedDate = LocalDate.now()
                pickedTime = LocalTime.now()
                selectedPractiseType = PractiseType.YOGA
                selectedTrainer = ""
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookSessionTopBar(
    onDeleteButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.book_session),
                style = MaterialTheme.typography.headlineSmall
            )},
        actions = {
            IconButton(onClick = onDeleteButtonClick) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.delete_your_progress_in_booking_session)
                )
            }
        }
    )
}
