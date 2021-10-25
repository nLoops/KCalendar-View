package com.thebluekernel.calendar.library.data.model

import org.junit.Assert
import org.junit.Test
import java.lang.Exception
import java.lang.NumberFormatException
import java.time.LocalDate
import java.time.ZoneOffset

/**
 * Created by Ahmed Ibrahim on 20,October,2021
 */
class CalendarDayTest {
    @Test
    fun `test getMillis() returns expected results`() {
        val now = LocalDate.now()
        val nowInMillis = now.atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli()
        val day = CalendarDay(now, DayOwner.CURRENT_MONTH, false)
        Assert.assertTrue(nowInMillis == day.getInMillis())

    }

    @Test
    fun `test getFormatted() date with valid pattern returns expected results`() {
        val day = CalendarDay(LocalDate.of(2017, 7, 14), isHijri = false)
        val formatted = day.getFormatted("yyyy-MM-dd")
        Assert.assertTrue(formatted == "2017-07-14")
    }

    @Test
    fun `test getFormatted() date with not valid pattern throw an exception`() {
        val day = CalendarDay(LocalDate.now(), isHijri = false)
        val exception = Assert.assertThrows(IllegalArgumentException::class.java) {
            day.getFormatted("mmmmmmmm")
        }
        val exceptionMessage = "Too many pattern letters: m"
        Assert.assertTrue(exception.localizedMessage == exceptionMessage)
    }

    @Test
    fun `when call getFormatted() with isHijri = true should returns expected results`() {
        val day = CalendarDay(LocalDate.of(2017, 7, 14), isHijri = true)
        val formattedInHijri = day.getFormatted("yyyy-MM-dd")
        Assert.assertTrue(formattedInHijri == "1438-10-20")
    }
}