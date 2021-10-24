package com.thebluekernel.calendar.library.data.model

import java.time.LocalDate
import java.time.ZoneOffset
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter

/**
 * Created by Ahmed Ibrahim on 20,October,2021
 */
data class CalendarDay(
    val date: LocalDate,
    val owner: DayOwner = DayOwner.CURRENT_MONTH,
    private val isHijri: Boolean = false
) {
    fun getFormatted(pattern: String): String {
        return when {
            isHijri -> getFormatter(pattern).format(convertToHijri())
            else -> getFormatter(pattern).format(date)
        }
    }

    fun getInMillis(): Long = date.atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli()

    private fun convertToHijri(): HijrahDate = HijrahDate.from(date)

    private fun getFormatter(pattern: String) = DateTimeFormatter.ofPattern(pattern)

}

enum class DayOwner {
    PREV_MONTH,
    CURRENT_MONTH,
    NEXT_MONTH
}