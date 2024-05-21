package com.example.shanti.presentation.home.book_session

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DatePicker
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shanti.components.PractiseTypeListSheet
import com.example.shanti.components.TrainerListSheet
import com.example.shanti.domain.model.Gender
import com.example.shanti.domain.model.PractiseType
import com.example.shanti.domain.model.Trainer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSessionScreen() {

    Text(text = "Book Visit Screen")

    val scope = rememberCoroutineScope()

    val datePickerState = rememberDatePickerState(initialDisplayedMonthMillis = null)

    val sheetState = rememberModalBottomSheetState()
    var isPractiseTypeSheetOpen by remember { mutableStateOf(false) }
    var isTrainerSheetOpen by remember { mutableStateOf(false) }
    var selectedPractiseType by remember { mutableStateOf(PractiseType.YOGA) }
    var selectedTrainer by remember { mutableStateOf("") }
    val practiseTypes = listOf(PractiseType.YOGA,PractiseType.MEDITATION, PractiseType.BOTH)
    val trainers = listOf(Trainer(name = "Franco", surname = "Parapallo", email = "franco.parapallo@mail.com", gender = Gender.MALE, practiseType = PractiseType.YOGA), Trainer(name = "Giacomo", surname = "Spuno", email = "giacomo.spuno@mail.com", gender = Gender.MALE, practiseType = PractiseType.MEDITATION))
    
    PractiseTypeListSheet(
        sheetState = sheetState,
        isOpen = isPractiseTypeSheetOpen,
        practiseTypes = practiseTypes,
        onDismissRequest = {isPractiseTypeSheetOpen = false},
        onPractiseTypeClicked = { practiseType ->
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
                onDeleteButtonClick = {}
            )
        }
    ) { contentPadding ->

            Column(
                modifier = Modifier
                        .padding(contentPadding)
                        .padding(horizontal = 12.dp)
            ) {

                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "${datePickerState.selectedDateMillis}"
                )

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
