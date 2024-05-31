package com.example.shanti.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.shanti.data.entity.converter.DateTypeConverter
import com.example.shanti.data.model.PractiseType
import com.example.shanti.data.model.Status
import java.text.SimpleDateFormat
import java.util.Date

@Entity
@TypeConverters(DateTypeConverter::class)
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
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
