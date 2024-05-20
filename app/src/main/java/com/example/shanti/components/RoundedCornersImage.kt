package com.example.shanti.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun RoundedCornersImage(imageRes: Int, modifier: Modifier) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    )
}