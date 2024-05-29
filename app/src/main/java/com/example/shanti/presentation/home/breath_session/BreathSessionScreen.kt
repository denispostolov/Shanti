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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shanti.components.CircularSlider
import com.example.shanti.ui.theme.Purple40
import com.example.shanti.ui.theme.Purple80
import kotlinx.coroutines.delay
import androidx.compose.runtime.*



//TODO: Check the timer and the method onEnd; it doesn't work correctly
@Composable
fun BreathSessionScreen() {
    var duration by remember { mutableStateOf(30f) }
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val vibrator = context.getSystemService(Vibrator::class.java)

    if (!isAnimating) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Durata (secondi): ${duration.toInt()}",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(contentAlignment = Alignment.Center) {
                CircularSlider(
                    onChange = { newValue -> duration = newValue * 110 + 10 }, // Scale 0-1 to 10-120
                    modifier = Modifier.size(400.dp),
                    progressColor = Purple40,
                    backgroundColor = Purple80,
                    thumbColor = Purple40,
                    stroke = 30f
                )
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Purple40),
                    onClick = { isAnimating = true }) {
                    Text("Avvia")
                }
            }
        }
    } else {
        BreathingAnimation(
            duration = duration.toInt(),
            onEnd = { isAnimating = false },
            vibrator = vibrator
        )
    }
}


@Composable
fun BreathingAnimation(duration: Int, onEnd: () -> Unit, vibrator: Vibrator) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val color by infiniteTransition.animateColor(
        initialValue = Purple40,
        targetValue = Purple80,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    var remainingTime by remember { mutableStateOf(duration) }
    var isBreathingIn by remember { mutableStateOf(true) }

    LaunchedEffect(duration) {
        val totalDuration = duration * 1000L // Convert to milliseconds
        val cycles = totalDuration / 4000L // Each cycle is 4 seconds (2 seconds in, 2 seconds out)
        repeat(cycles.toInt()) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            delay(4000L) // Wait for inhale
            isBreathingIn = false
            delay(4000L) // Wait for exhale
            isBreathingIn = true
        }
        onEnd()
    }

    LaunchedEffect(remainingTime) {
        while (remainingTime > 0) {
            delay(1000L)
            remainingTime -= 1
        }
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
                .background(color, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isBreathingIn) "Breath In" else "Breath Out",
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
//        Text(
//            text = "Tempo rimanente: ${remainingTime}s",
//            modifier = Modifier.align(Alignment.TopCenter),
//            style = MaterialTheme.typography.titleLarge,
//            color = Color.Black
//        )
        LinearProgressTimer(duration = duration, remainingTime = remainingTime)
    }
}

@Composable
fun LinearProgressTimer(duration: Int, remainingTime: Int) {
    val progress = remember(remainingTime) { 1f - (remainingTime.toFloat() / duration) }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .padding(horizontal = 16.dp),
            color = Purple40,
        )
    }
}
