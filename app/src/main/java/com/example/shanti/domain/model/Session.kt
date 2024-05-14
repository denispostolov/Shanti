package com.example.shanti.domain.model

import java.util.Date

data class Session(
    val id: String,
    val dateTime: Date,
    val trainerName: String,
    val trainerSurname: String,
    val status: Status,
    val urlMeet: String
) {
    fun trainerFullName(): String {
        return trainerName.plus(" ").plus(trainerSurname)
    }
}
