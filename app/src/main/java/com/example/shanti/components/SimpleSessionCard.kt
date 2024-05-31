package com.example.shanti.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shanti.R
import com.example.shanti.data.entity.SessionEntity
import com.example.shanti.data.model.PractiseType
import com.example.shanti.data.model.Status
import com.example.shanti.ui.theme.Purple40
import com.example.shanti.ui.theme.color1

@Composable
fun SimpleSessionCard(
    session: SessionEntity,
    onClick: (session: SessionEntity) -> Unit
) {

    val imageRes = when (session.practiseType) {
        PractiseType.YOGA -> R.drawable.yoga_card_image
        PractiseType.MEDITATION -> R.drawable.meditation_card_image
        else -> R.drawable.yoga_meditation_image_card

    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick(session) }
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Purple40,
                            color1
                        )
                    )
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.7f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RoundedCornersImage(imageRes = imageRes, modifier = Modifier)
                }

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = stringResource(R.string.trainer_card), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = session.trainerFullName(), color = MaterialTheme.colorScheme.onPrimary)
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = stringResource(R.string.date), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = session.formattedDate(), color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = stringResource(R.string.at), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = session.time, color = MaterialTheme.colorScheme.onPrimary)
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = stringResource(R.string.status), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = session.status.toString(), color = MaterialTheme.colorScheme.onPrimary)
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = stringResource(R.string.type_of_practise), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = session.practiseType.toString(), color = MaterialTheme.colorScheme.onPrimary)
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = stringResource(R.string.google_meet_room), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    if (session.status == Status.FUTURE) {
                        ClickableLinkText(url = session.urlMeet, colorText = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text(text = session.urlMeet, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }

        }
    }
}