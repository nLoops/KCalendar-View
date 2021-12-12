package com.thebluekernel.calendar.library.data.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.thebluekernel.calendar.library.data.model.CalendarMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.time.temporal.WeekFields
import java.util.*

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */

internal fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

internal fun daysOfWeekFromLocale(startDayOfWeek: DayOfWeek): Array<DayOfWeek> {
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (startDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(startDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until startDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}

internal fun LocalDate.toHijri() = HijrahDate.from(this)

internal fun todayInHijri() = LocalDate.now().toHijri()

internal fun today() = LocalDate.now()

internal val YearMonth.next: YearMonth
    get() = this.plusMonths(1)

internal val YearMonth.previous: YearMonth
    get() = this.minusMonths(1)

internal fun YearMonth.monthName(isHijri: Boolean, locale: Locale = Locale.getDefault()) =
    when (isHijri) {
        true -> firstDay().toHijri().formatWithPattern(MONTH_NAME_PATTERN, locale)
        false -> firstDay().formatWithPattern(MONTH_NAME_PATTERN, locale)
    }

internal fun YearMonth.daysInMonthLength(isHijri: Boolean) = when (isHijri) {
    true -> firstDay().toHijri().lengthOfMonth()
    else -> this.lengthOfMonth()
}


fun TemporalAccessor.formatWithPattern(
    pattern: String,
    locale: Locale = Locale.getDefault()
): String {
    val formatter = DateTimeFormatter.ofPattern(pattern, locale)
    return formatter.format(this)
}

fun YearMonth.firstDay() = LocalDate.of(this.year, this.monthValue, 1)

fun YearMonth.yearValue(isHijri: Boolean) = when (isHijri) {
    true -> firstDay().toHijri().formatWithPattern(HIJRI_YEAR_NAME_PATTERN).toInt()
    else -> this.year
}

fun YearMonth.monthVal(isHijri: Boolean) = when (isHijri) {
    true -> firstDay().toHijri().formatWithPattern(HIJRI_MONTH_VALUE_PATTERN).toInt()
    else -> this.monthValue
}

internal const val LAST_MONTH_OF_YEAR_INDEX = 12
internal const val FIRST_MONTH_OF_YEAR_INDEX = 1
internal const val WEEK_DAYS = 7
internal const val NO_INDEX = -1
internal const val MONTH_NAME_PATTERN = "MMMM"
internal const val HIJRI_YEAR_NAME_PATTERN = "yyyy"
internal const val HIJRI_MONTH_VALUE_PATTERN = "MM"