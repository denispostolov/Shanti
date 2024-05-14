package com.example.shanti.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shanti.domain.model.Gender
import com.example.shanti.domain.model.PractiseType

@Entity
data class TrainerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val surname: String,
    val email: String,
    val gender: Gender,
    val practiseType: PractiseType

)
