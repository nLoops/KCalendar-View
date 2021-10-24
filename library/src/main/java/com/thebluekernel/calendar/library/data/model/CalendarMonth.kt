package com.thebluekernel.calendar.library.data.model

import com.thebluekernel.calendar.library.data.model.CalendarMonth.Companion.WEEK_DAYS
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */
data class CalendarMonth(
    internal val month: YearMonth,
    internal val firstDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY
) {
    internal val days = generateWeekDays()

    fun getMonthName() = month.month.name

    override fun toString() = "CalendarMonth[startDay: ${days.flatten().first()} and endDay: ${days.flatten().last()}]"

    internal companion object {
        const val WEEK_DAYS = 7
    }
}

internal fun CalendarMonth.generateWeekDays(): MutableList<List<CalendarDay>> {
    val year = month.year
    val monthValue = month.monthValue
    val thisMonthDays = (1..month.lengthOfMonth()).map { day ->
        CalendarDay(LocalDate.of(year, monthValue, day), DayOwner.CURRENT_MONTH)
    }
    return thisMonthDays.chunked(WEEK_DAYS).toMutableList()
}
