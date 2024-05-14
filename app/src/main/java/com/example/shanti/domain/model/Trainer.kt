package com.example.shanti.domain.model

data class Trainer(
    val name: String,
    val surname: String,
    val email: String,
    val gender: Gender,
    val practiseType: PractiseType
) {
    fun fullName(): String {
        return name.plus(" ").plus(surname)
    }
}