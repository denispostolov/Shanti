package com.example.shanti.navigation.screen
sealed class HomeScreen(val route: String, val name: String) {
    object Home :
        HomeScreen(route = "HOME", name = "Home")

    object BookSession :
        HomeScreen(route = "BOOK_SESSION", name = "Book Session")

    object BreathSession :
        HomeScreen(route = "BREATH_SESSION", name = "Breath")

    object Profile:
        HomeScreen(route = "PROFILE", name = "Profile")

}