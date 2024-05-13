package com.example.shanti.navigation.screen
sealed class HomeScreen(val route: String, val name: String) {
    object Home :
        HomeScreen(route = "HOME", name = "Home")

    object BookLessons :
        HomeScreen(route = "BOOK_LESSONS", name = "Book Lessons")

    object Profile:
        HomeScreen(route = "PROFILE", name = "Profile")

}