package com.example.shanti.test

import com.example.shanti.common.defineStatus
import com.example.shanti.common.getTrainerNameAndSurnameFromFullName
import com.example.shanti.domain.model.Status
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.time.LocalTime
import java.util.Date

class UtilsTest {
    @Test
    fun testGetTrainerNameAndSurnameFromFullNameWithFullName() {
        val fullName = "John Doe"
        val result = getTrainerNameAndSurnameFromFullName(fullName)
        assertEquals(Pair("John", "Doe"), result)
    }

    @Test
    fun testGetTrainerNameAndSurnameFromFullNameWithOnlyName() {
        val fullName = "John"
        val result = getTrainerNameAndSurnameFromFullName(fullName)
        assertEquals(Pair("John", ""), result)
    }

    @Test
    fun testGetTrainerNameAndSurnameFromFullNameWithEmptyString() {
        val fullName = ""
        val result = getTrainerNameAndSurnameFromFullName(fullName)
        assertEquals(Pair("", ""), result)
    }

    @Test
    fun testGetTrainerNameAndSurnameFromFullNameWithMultipleSpaces() {
        val fullName = "John    Doe"
        val result = getTrainerNameAndSurnameFromFullName(fullName.trim().replace("\\s+".toRegex(), " "))
        assertEquals(Pair("John", "Doe"), result)
    }

    @Test
    fun testGetTrainerNameAndSurnameFromFullNameWithLeadingAndTrailingSpaces() {
        val fullName = "  John Doe  "
        val result = getTrainerNameAndSurnameFromFullName(fullName.trim())
        assertEquals(Pair("John", "Doe"), result)
    }
}

class DefineStatusTest {
    @Test
    fun testDefineStatusWithFutureDate() {
        val futureDate = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000) // Tomorrow
        val time = LocalTime.now()
        val result = defineStatus(futureDate, time)
        assertEquals(Status.FUTURE, result)
    }

    @Test
    fun testDefineStatusWithPastDate() {
        val pastDate = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000) // Yesterday
        val time = LocalTime.now()
        val result = defineStatus(pastDate, time)
        assertEquals(Status.PASSED, result)
    }

    @Test
    fun testDefineStatusWithTodayDateAndFutureTime() {
        val todayDate = Date()
        val futureTime = LocalTime.now().plusHours(1)
        val result = defineStatus(todayDate, futureTime)
        assertEquals(Status.FUTURE, result)
    }

    @Test
    fun testDefineStatusWithTodayDateAndPastTime() {
        val todayDate = Date()
        val pastTime = LocalTime.now().minusHours(1)
        val result = defineStatus(todayDate, pastTime)
        assertEquals(Status.PASSED, result)
    }
}

