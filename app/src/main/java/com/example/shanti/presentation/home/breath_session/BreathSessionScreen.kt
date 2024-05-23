package com.example.shanti.presentation.home.breath_session

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.shanti.components.CircularSlider
import com.example.shanti.ui.theme.Purple40
import com.example.shanti.ui.theme.Purple80
import kotlinx.coroutines.delay

@Composable
fun BreathSessionScreen() {
    var duration by remember { mutableStateOf(30f) }
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val vibrator = context.getSystemService(Vibrator::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Durata (secondi): ${duration.toInt()}")
        CircularSlider(
            onChange = { newValue -> duration = newValue * 110 + 10 }, // Scale 0-1 to 10-120
            modifier = Modifier.size(200.dp),
            progressColor = Purple80,
            backgroundColor = Purple40,
            thumbColor = Purple80,
            stroke = 15f
        )
//        Slider(
//            value = duration,
//            onValueChange = { duration = it },
//            valueRange = 10f..120f, // Range from 10 to 120 seconds
//            steps = 11, // 11 steps for 10 seconds increments
//            modifier = Modifier.fillMaxWidth()
//        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { isAnimating = true }) {
            Text("Avvia")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (isAnimating) {
            BreathingAnimation(
                duration = duration.toInt(),
                onEnd = { isAnimating = false },
                vibrator = vibrator
            )
        }
    }
}

@Composable
fun BreathingAnimation(duration: Int, onEnd: () -> Unit, vibrator: Vibrator) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val color by infiniteTransition.animateColor(
        initialValue = Purple80,
        targetValue = Purple40,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(duration) {
        val totalDuration = duration * 1000L // Convert to milliseconds
        val cycles = totalDuration / 4000L // Each cycle is 4 seconds (2 seconds in, 2 seconds out)
        repeat(cycles.toInt()) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            delay(4000L) // Wait for one full breath cycle (2 seconds in, 2 seconds out)
        }
        onEnd()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .scale(scale)
                .background(color, shape = CircleShape)
        )
    }
}