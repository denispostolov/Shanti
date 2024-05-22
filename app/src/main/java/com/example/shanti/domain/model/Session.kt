package com.example.shanti.domain.model

import java.text.SimpleDateFormat
import java.util.Date

data class Session(
    val id: String,
    val dateTime: Date,
    val time: String,
    val trainerName: String,
    val trainerSurname: String,
    val status: Status,
    val practiseType: PractiseType,
    val urlMeet: String
) {
    fun trainerFullName(): String {
        return trainerName.plus(" ").plus(trainerSurname)
    }

    fun formattedDate(): String {
        return SimpleDateFormat("dd-MM-yyyy").format(dateTime)
    }
}
