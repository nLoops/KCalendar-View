package com.thebluekernel.calendar.library.data.model

import com.thebluekernel.calendar.library.data.utils.*
import org.junit.Assert
import org.junit.Test
import java.time.DateTimeException
import java.time.DayOfWeek
import java.time.YearMonth

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */
class CalendarMonthTest {

    private val dayOfWeek by lazy { DayOfWeek.SUNDAY }
    private val yearMonth by lazy { YearMonth.of(2017, 7) }

    @Test
    fun `when call month name returns expected results`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, false)
        Assert.assertTrue(month.month.monthName(false) == "July")
    }

    @Test
    fun `when isHijri = true month name should returns as expected`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, true)
        Assert.assertTrue(month.month.monthName(true) == "Shawwal")
    }

    @Test
    fun `when generate month days returns expected results`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, false)
        val monthDays = month.days.flatten()
        Assert.assertTrue(monthDays.isNotEmpty())
    }

    @Test
    fun `when enter invalid month through a DateTimeException`() {
        Assert.assertThrows(DateTimeException::class.java) {
            CalendarMonth(YearMonth.of(2019, -1), dayOfWeek, false)
        }
    }

    @Test
    fun `when call ext firstDay() && isHijri = false should returns first day of date of month`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, false)
        val formatted = month.month.firstDay().formatWithPattern("yyyy-MM-dd")
        Assert.assertTrue(formatted == "2017-07-01")
    }

    @Test
    fun `when call ext firstDay() && isHijri = true should returns first day of date of month`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, false)
        val formatted = month.month.firstDay().toHijri().formatWithPattern("yyyy-MM-dd")
        Assert.assertTrue(formatted == "1438-10-07")
    }

    @Test
    fun `when call ext yearValue() && isHijri = false should returns expected year value`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, false)
        val formatted = month.month.yearValue(false)
        Assert.assertTrue(formatted == 2017)
    }

    @Test
    fun `when call ext yearValue() && isHijri = true should returns expected year value`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, true)
        val formatted = month.month.yearValue(true)
        Assert.assertTrue(formatted == 1438)
    }

    @Test
    fun `when call ext monthVal() && isHijri = false should returns expected year value`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, false)
        val formatted = month.month.monthVal(false)
        Assert.assertTrue(formatted == 7)
    }

    @Test
    fun `when call ext monthVal() && isHijri = true should returns expected year value`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, true)
        val formatted = month.month.monthVal(true)
        Assert.assertTrue(formatted == 10)
    }

    @Test
    fun `when call ext daysInMonthLength() && isHijri = false should returns expected year value`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, false)
        val formatted = month.month.daysInMonthLength(false)
        Assert.assertTrue(formatted == 31)
    }

    @Test
    fun `when call ext daysInMonthLength() && isHijri = true should returns expected year value`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, true)
        val formatted = month.month.daysInMonthLength(true)
        Assert.assertTrue(formatted == 29)
    }

    @Test
    fun `when generate CalendarDays of month && isHijri = false should first day date returns as expected`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, false)
        // we need only current month no need to check inDates from last month
        val firstCalendarDay = month.days.flatten()
            .filter { it.owner == DayOwner.CURRENT_MONTH }[0].getFormatted("yyyy-MM-dd")
        Assert.assertTrue(firstCalendarDay == "2017-07-01")
    }

    @Test
    fun `when generate CalendarDays of month && isHijri = true should first day date returns as expected`() {
        val month = CalendarMonth(yearMonth, dayOfWeek, true)
        // we need only current month no need to check inDates from last month
        val firstCalendarDay = month.days.flatten()
            .filter { it.owner == DayOwner.CURRENT_MONTH }[0].getFormatted("yyyy-MM-dd")
        Assert.assertTrue(firstCalendarDay == "1438-10-01")
    }

}