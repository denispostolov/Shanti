package com.example.shanti.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shanti.domain.model.Status
import java.util.Date

@Entity
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dateTime: Date,
    val trainerName: String,
    val trainerSurname: String,
    val status: Status,
    val urlMeet: String
)
