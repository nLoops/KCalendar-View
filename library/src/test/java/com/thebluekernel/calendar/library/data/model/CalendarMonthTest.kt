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
        val month = CalendarMonth(YearMonth.of(2021, 10))
        Assert.assertTrue(month.getMonthName() == "OCTOBER")
    }

    @Test
    fun `when generate month days returns expected results`() {
        val month = CalendarMonth(YearMonth.of(2019, 5))
        val monthDays = month.days.flatten()
        Assert.assertTrue(monthDays.isNotEmpty())
    }

    @Test
    fun `when enter invalid month through a DateTimeException`() {
        Assert.assertThrows(DateTimeException::class.java) {
            CalendarMonth(YearMonth.of(2019, -1))
        }
    }
}