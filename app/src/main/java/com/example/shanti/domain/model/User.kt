package com.example.shanti.domain.model

data class User (
    val name: String,
    val surname: String,
    val email: String,
    val gender: Gender
) {
    fun fullName(): String {
        return name.plus(" ").plus(surname)
    }
}

