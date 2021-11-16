package com.thebluekernel.calendar.library.data.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
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

internal val YearMonth.next: YearMonth
    get() = this.plusMonths(1)

internal val YearMonth.previous: YearMonth
    get() = this.minusMonths(1)

internal fun TemporalAccessor.formatWithPattern(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(this)
}

internal fun LocalDate.toHijri() = HijrahDate.from(this)

internal fun todayInHijri() = LocalDate.now().toHijri()

fun daysOfWeekFromLocale(startDayOfWeek: DayOfWeek): Array<DayOfWeek> {
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

internal const val NO_INDEX = -1