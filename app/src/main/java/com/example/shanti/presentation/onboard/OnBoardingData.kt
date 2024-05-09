package com.example.shanti.presentation.onboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

data class OnBoardingData(val url: String, val title: String, val desc: String)

@Composable
fun LoaderIntro(modifier: Modifier, url: String){
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url(url))

    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}


