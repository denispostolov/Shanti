package com.example.shanti.presentation.onboard
import android.media.session.MediaSessionManager
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


@Composable
fun OnboardScreen(
   rootNavHostController: NavHostController,
){
    val items = ArrayList<OnBoardingData>()

    items.add(
        OnBoardingData(
            url = "https://lottie.host/56b1b0b9-5052-40f3-a4fc-f66a68c4c083/WqMQ6UdoKN.json",
            "Meditation is your way",
            ""
        )
    )

    items.add(
        OnBoardingData(
            url = "https://lottie.host/3be6ea4a-1fa6-4313-a601-008bad4220cb/HEdpwg9lfI.json",
            "Yoga is power",
            ""
        )
    )

    items.add(
        OnBoardingData(
            url = "https://lottie.host/75718fc3-ffb1-48a5-aabf-d5b4f74fa9be/YDANP9jBT3.json",
            "Take a breath",
            ""
        )
    )
}