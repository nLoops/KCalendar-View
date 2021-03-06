package com.thebluekernel.calendar.library.data.model

import com.thebluekernel.calendar.library.data.utils.DateTimeUtils
import com.thebluekernel.calendar.library.data.utils.formatWithPattern
import com.thebluekernel.calendar.library.data.utils.toHijri
import com.thebluekernel.calendar.library.data.utils.today
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset

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

    fun getInGregorianString() = date.toString()

    fun getInMillis(): Long =
        date.atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli()

    fun isBeforeToday() = date.isBefore(today())

    fun isEqualToday() = date.isEqual(today())

    fun isAfterToday() = date.isAfter(today())

    fun isEqualTo(other: LocalDate) = date.isEqual(other)

    internal fun getMonth(): YearMonth = YearMonth.of(date.year, date.monthValue)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CalendarDay
        return date == other.date && owner == other.owner
    }

    override fun hashCode(): Int {
        return 31 * (date.hashCode() + owner.hashCode())
    }

    companion object {
        fun of(dateString: String, isHijri: Boolean = false): CalendarDay {
            val date = DateTimeUtils.parseDate(dateString)
            return CalendarDay(date, isHijri = isHijri)
        }
    }
}

/**
 * enum to inform which month own specific [CalendarDay]
 */
enum class DayOwner {
    PREV_MONTH,
    CURRENT_MONTH,
    NEXT_MONTH
}