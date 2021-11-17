package com.thebluekernel.calendar.library.data.model

import com.thebluekernel.calendar.library.data.utils.*
import com.thebluekernel.calendar.library.data.utils.daysInMonthLength
import com.thebluekernel.calendar.library.data.utils.monthName
import com.thebluekernel.calendar.library.data.utils.monthVal
import com.thebluekernel.calendar.library.data.utils.yearValue
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.chrono.HijrahDate
import java.time.temporal.WeekFields
import java.util.*

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 *
 * @param month represent current month object
 * @param firstDayOfWeek represent firstDayOfWeek by default SUNDAY
 * @param isHijri flag to tell current day to render into Gregorien or Hijri format
 *
 * @see CalendarDay
 *
 */
data class CalendarMonth(
    val month: YearMonth,
    internal val firstDayOfWeek: DayOfWeek,
    internal val isHijri: Boolean
) {
    internal val days = generateWeekDays()

    override fun toString() =
        "CalendarMonth[Year: ${month.yearValue(isHijri)} - Month: ${month.monthVal(isHijri)}]"
}

internal fun CalendarMonth.generateWeekDays(): MutableList<List<CalendarDay>> {
    val year = month.yearValue(isHijri)
    val monthValue = month.monthVal(isHijri)
    val thisMonthDays = (1..month.daysInMonthLength(isHijri)).map { day ->
        val dayDate = when (isHijri) {
            true -> LocalDate.from(HijrahDate.of(year, monthValue, day))
            else -> LocalDate.of(year, monthValue, day)
        }
        CalendarDay(dayDate, DayOwner.CURRENT_MONTH, isHijri)
    }
    val weekDaysGroup = run {
        // Group days by week of month so we can add the in dates if necessary.
        val weekOfMonthField = WeekFields.of(firstDayOfWeek, 1).weekOfMonth()
        val groupByWeekOfMonth =
            thisMonthDays.groupBy {
                when (isHijri) {
                    true -> it.date.toHijri().get(weekOfMonthField)
                    false -> it.date.get(weekOfMonthField)
                }
            }.values.toMutableList()

        // Add in-dates if necessary
        val firstWeek = groupByWeekOfMonth.first()
        if (firstWeek.size < 7) {
            val previousMonth = month.previous
            val prevYear = previousMonth.yearValue(isHijri)
            val prevMonthValue = previousMonth.monthVal(isHijri)
            val inDates = (1..previousMonth.daysInMonthLength(isHijri)).toList()
                .takeLast(7 - firstWeek.size).map { day ->
                    val dayDate = when (isHijri) {
                        true -> LocalDate.from(HijrahDate.of(prevYear, prevMonthValue, day))
                        else -> LocalDate.of(prevYear, prevMonthValue, day)
                    }
                    CalendarDay(
                        dayDate,
                        DayOwner.PREV_MONTH,
                        isHijri
                    )
                }
            groupByWeekOfMonth[0] = inDates + firstWeek
        }
        groupByWeekOfMonth
    }
    return weekDaysGroup
}

fun CalendarMonth.monthName(locale: Locale = Locale.getDefault()) = this.month.monthName(isHijri,locale)

fun CalendarMonth.yearValue() = this.month.yearValue(isHijri)
