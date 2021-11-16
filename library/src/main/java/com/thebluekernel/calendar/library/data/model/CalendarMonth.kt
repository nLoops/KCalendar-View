package com.thebluekernel.calendar.library.data.model

import com.thebluekernel.calendar.library.data.utils.formatWithPattern
import com.thebluekernel.calendar.library.data.utils.toHijri
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 *
 * @param month represent current month object
 * @param firstDayOfWeek represent firstDayOfWeek by default SUNDAY
 * @param isHijri flag to tell current day to render into Gregorien or Hijri format
 */
data class CalendarMonth(
    internal val month: YearMonth,
    internal val firstDayOfWeek: DayOfWeek,
    internal val isHijri: Boolean
) {
    internal val days = generateWeekDays()

    fun getMonthName() = when (isHijri) {
        true -> getFirstDay().toHijri().formatWithPattern(MONTH_NAME_PATTERN)
        false -> getFirstDay().formatWithPattern(MONTH_NAME_PATTERN)
    }

    fun getYear() = when (isHijri) {
        true -> getFirstDay().toHijri().formatWithPattern(HIJRI_YEAR_NAME_PATTERN)
        else -> month.year.toString()
    }

    fun getHijriMonthValue() =
        getFirstDay().toHijri().formatWithPattern(HIJRI_MONTH_VALUE_PATTERN).toInt()

    internal fun getFirstDay() = LocalDate.of(month.year, month.monthValue, 1)

    override fun toString() =
        "CalendarMonth[${month.monthValue} - ${month.year}]"

    internal companion object {
        const val MONTH_NAME_PATTERN = "MMMM"
        const val HIJRI_YEAR_NAME_PATTERN = "yyyy"
        const val HIJRI_MONTH_VALUE_PATTERN = "MM"
    }
}

internal fun CalendarMonth.generateWeekDays(): MutableList<List<CalendarDay>> {
    val year = month.year
    val monthValue = month.monthValue
    val thisMonthDays = (1..month.lengthOfMonth()).map { day ->
        CalendarDay(LocalDate.of(year, monthValue, day), DayOwner.CURRENT_MONTH, isHijri)
    }
    val weekDaysGroup = run {
        // Group days by week of month so we can add the in dates if necessary.
        val weekOfMonthField = WeekFields.of(firstDayOfWeek, 1).weekOfMonth()
        val groupByWeekOfMonth =
            thisMonthDays.groupBy { it.date.get(weekOfMonthField) }.values.toMutableList()

        // Add in-dates if necessary
        val firstWeek = groupByWeekOfMonth.first()
        if (firstWeek.size < 7) {
            val previousMonth = month.minusMonths(1)
            val inDates = (1..previousMonth.lengthOfMonth()).toList()
                .takeLast(7 - firstWeek.size).map {
                    CalendarDay(
                        LocalDate.of(previousMonth.year, previousMonth.month, it),
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
