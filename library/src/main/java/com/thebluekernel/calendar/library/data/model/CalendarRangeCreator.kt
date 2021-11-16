package com.thebluekernel.calendar.library.data.model

import com.thebluekernel.calendar.library.data.utils.next
import java.time.DayOfWeek
import java.time.YearMonth

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */
internal data class CalendarRangeCreator(
    internal val startMonth: YearMonth,
    internal val endMonth: YearMonth,
    internal val firstDayOfWeek: DayOfWeek,
    internal val isHijri: Boolean
) {
    internal val months = run {
        return@run generateMonths()
    }

    private fun generateMonths(): List<CalendarMonth> {
        var currentMonth = startMonth
        val calendarMonths = mutableListOf<CalendarMonth>()
        while (startMonth <= endMonth) {
            calendarMonths.add(CalendarMonth(currentMonth, firstDayOfWeek, isHijri))
            if (currentMonth != endMonth) currentMonth = currentMonth.next else break
        }
        return calendarMonths
    }
}
