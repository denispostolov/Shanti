package com.example.shanti.common

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.example.shanti.domain.model.Status
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date


fun showDatePickerDialog(context: Context, initialDate: LocalDate, onDateSelected: (Int, Int, Int) -> Unit) {
    val year = initialDate.year
    val month = initialDate.monthValue - 1 // Month is 0-based in Calendar
    val day = initialDate.dayOfMonth

    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateSelected(selectedYear, selectedMonth, selectedDay)
        }, year, month, day).show()
}

fun showTimePickerDialog(context: Context, initialTime: LocalTime, onTimeSelected: (Int, Int) -> Unit) {
    val hour = initialTime.hour
    val minute = initialTime.minute

    TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            onTimeSelected(selectedHour, selectedMinute)
        }, hour, minute, true).show()
}

fun localDateToDate(localDate: LocalDate): Date {
    val localDateTime = localDate.atStartOfDay()
    val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
    return Date.from(instant)
}

fun getTrainerNameAndSurnameFromFullName(trainer: String): Pair<String, String> {
    val trainerNameParts = trainer.split(" ", limit = 2)
    val trainerName = trainerNameParts[0]
    val trainerSurname = if (trainerNameParts.size > 1) trainerNameParts[1] else ""

    return Pair(trainerName,trainerSurname)
}

fun defineStatus(pickedDate: Date, pickedTime: LocalTime): Status {
    val todayDate = Date()
    val actualTime = LocalTime.now()
    var result = Status.FUTURE
    if(pickedDate < todayDate){
        result = Status.PASSED
    } else if (pickedDate == todayDate){
        if(pickedTime < actualTime){
            result = Status.PASSED
        }
    }

    return result

}