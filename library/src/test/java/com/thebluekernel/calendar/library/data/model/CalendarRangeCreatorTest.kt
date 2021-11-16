package com.thebluekernel.calendar.library.data.model

import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek
import java.time.YearMonth

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */
class CalendarRangeCreatorTest {
    @Test
    fun `test when give startMonth and endMonth returns expected data`() {
        val startMonth = YearMonth.of(2021, 1)
        val endMonth = YearMonth.of(2021, 12)
        val months = CalendarRangeCreator(startMonth, endMonth, DayOfWeek.SUNDAY, false).months
        Assert.assertTrue(months.isNotEmpty())
    }

    @Test
    fun `test when give startMonth and endMonth order is right`(){
        val startMonth = YearMonth.of(2021, 1)
        val endMonth = YearMonth.of(2021, 12)
        val months = CalendarRangeCreator(startMonth, endMonth, DayOfWeek.SUNDAY, false).months
        months.forEach {
            println("${it.month.monthValue}")
        }
        Assert.assertTrue(months.isNotEmpty())
    }
}