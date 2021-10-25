package com.thebluekernel.calendar.library.data.model

import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek
import java.time.YearMonth

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */
class CalendarMonthConfigTest {
    @Test
    fun `test when give startMonth and endMonth returns expected data`() {
        val startMonth = YearMonth.of(2021, 1)
        val endMonth = YearMonth.of(2021, 6)
        val months = CalendarMonthConfig(startMonth, endMonth,DayOfWeek.SUNDAY,false).months
        Assert.assertTrue(months.isNotEmpty() && months.size == 6)
    }
}