package com.example.shanti.test

import com.example.shanti.common.getTrainerNameAndSurnameFromFullName
import junit.framework.Assert.assertEquals
import org.junit.Test

class MathUtilsTest {

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
