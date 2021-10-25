package com.thebluekernel.calendar.library.data.model

import com.thebluekernel.calendar.library.data.utils.formatWithPattern
import com.thebluekernel.calendar.library.data.utils.toHijri
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter

/**
 * Created by Ahmed Ibrahim on 20,October,2021
 */
data class CalendarDay(
    private val date: LocalDate,
    private val owner: DayOwner = DayOwner.CURRENT_MONTH,
    private val isHijri: Boolean
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

enum class DayOwner {
    PREV_MONTH,
    CURRENT_MONTH,
    NEXT_MONTH
}