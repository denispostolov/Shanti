package com.example.shanti.data.mappers

import com.example.shanti.data.entity.SessionEntity
import com.example.shanti.domain.model.Session

fun SessionEntity.toSession(): Session {
    return Session(
        id = id.toString(),
        dateTime = dateTime,
        trainerName = trainerName,
        trainerSurname = trainerSurname,
        status = status,
        practiseType = practiseType,
        urlMeet = urlMeet
    )
}