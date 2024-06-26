package com.example.shanti.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shanti.R
import com.example.shanti.data.entity.TrainerEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainerListSheet(
    sheetState: SheetState,
    isOpen: Boolean,
    trainers: List<TrainerEntity>,
    bottomSheetTitle: String = stringResource(R.string.select_trainer),
    onTrainerClicked: (TrainerEntity) -> Unit,
    onDismissRequest: () -> Unit
) {
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
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(trainers) { trainer ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTrainerClicked(trainer) }
                            .padding(8.dp)
                    ) {
                        Text(text = trainer.fullName())
                    }
                }

            }
        }
    }

}