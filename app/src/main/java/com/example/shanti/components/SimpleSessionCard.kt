package com.example.shanti.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import com.example.shanti.domain.model.Session
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shanti.data.entity.SessionEntity
import com.example.shanti.ui.theme.Purple40
import com.example.shanti.ui.theme.color1
import com.example.shanti.ui.theme.color2

@Composable
fun SimpleSessionCard(
    session: Session
) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Trainer", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = session.trainerFullName(), color = MaterialTheme.colorScheme.onPrimary)
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Date:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = session.formattedDate(), color = MaterialTheme.colorScheme.onPrimary)
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Status:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = session.status.toString(), color = MaterialTheme.colorScheme.onPrimary)
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Google meet room:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = session.urlMeet, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }


}