package com.thebluekernel.calendar.library.data.model

import com.thebluekernel.calendar.library.data.utils.formatWithPattern
import com.thebluekernel.calendar.library.data.utils.toHijri
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

/**
 * Created by Ahmed Ibrahim on 20,October,2021
 *
 * @param date represent the current date of the day
 * @param owner represent the month whom own this date
 * @param isHijri flag to tell current day to render into Gregorien or Hijri format
 *
 * @see DayOwner
 */
data class CalendarDay(
    val date: LocalDate,
    val owner: DayOwner = DayOwner.CURRENT_MONTH,
    internal val isHijri: Boolean
) {
    fun getFormatted(pattern: String): String {
        return when {
            isHijri -> date.toHijri().formatWithPattern(pattern)
            else -> date.formatWithPattern(pattern)
        }
    }

    fun getInMillis(): Long =
        date.atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli()

}

/**
 * enum to inform which month own specific [CalendarDay]
 */
enum class DayOwner {
    PREV_MONTH,
    CURRENT_MONTH,
    NEXT_MONTH
}