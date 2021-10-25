package com.thebluekernel.calendar.library.data.model

import org.junit.Assert
import org.junit.Test
import java.time.DateTimeException
import java.time.YearMonth

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */
class CalendarMonthTest {
    @Test
    fun `when call month name returns expected results`() {
        val month = CalendarMonth(YearMonth.of(2021, 10), isHijri = false)
        Assert.assertTrue(month.getMonthName() == "October")
    }

    @Test
    fun `when generate month days returns expected results`() {
        val month = CalendarMonth(YearMonth.of(2019, 5), isHijri = false)
        val monthDays = month.days.flatten()
        Assert.assertTrue(monthDays.isNotEmpty())
    }

    @Test
    fun `when enter invalid month through a DateTimeException`() {
        Assert.assertThrows(DateTimeException::class.java) {
            CalendarMonth(YearMonth.of(2019, -1), isHijri = false)
        }
    }

    @Test
    fun `when isHijri = true month name should returns as expected`() {
        val month = CalendarMonth(YearMonth.of(2021, 10), isHijri = true)
        Assert.assertTrue(month.getMonthName() == "Safar")
    }
}