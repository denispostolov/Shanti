package com.example.shanti.data.mappers

import com.example.shanti.data.entity.TrainerEntity
import com.example.shanti.domain.model.Session
import com.example.shanti.domain.model.Trainer

fun TrainerEntity.toTrainer(): Trainer {
    return Trainer(
        name = name,
        surname = surname,
        email = email,
        gender = gender,
        practiseType = practiseType

    )
}