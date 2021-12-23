package com.thebluekernel.calendar.library.data.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Created by Ahmed Ibrahim on 23,December,2021
 */
internal object DateTimeUtils {

    private const val yyyyMMddTHHmmssZ = "yyyy-MM-dd'T'HH:mm:ssZ"

    fun parseDate(dateString: String): LocalDate = try {
        LocalDate.parse(dateString)
    } catch (e: Exception) {
        val dateFormatter = DateTimeFormatter.ofPattern(yyyyMMddTHHmmssZ)
        LocalDate.parse(dateString, dateFormatter)
    }

    private val DATE_PATTERNS_REGEX = mapOf(
        "^\\d{4}-\\d{1,2}-\\d{1,2}-\\d{1,2}-\\d{1,2}\$" to "yyyy-MM-dd-HH-mm",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{6}\\d{0,6}$" to "yyyy-M-d'T'HH:mm:ss.SSSSSS",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{5}\\d{0,6}$" to "yyyy-M-d'T'HH:mm:ss.SSSSS",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{4}\\d{0,6}$" to "yyyy-M-d'T'HH:mm:ss.SSSS",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{3}\\d{0,6}$" to "yyyy-M-d'T'HH:mm:ss.SSS",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{2}\\d{0,6}$" to "yyyy-M-d'T'HH:mm:ss.SS",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1}\\d{0,6}$" to "yyyy-M-d'T'HH:mm:ss.S",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\d{0,6}$" to "yyyy-M-d'T'HH:mm:ss",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{6}\\w$" to "yyyy-M-d'T'HH:mm:ss.SSSSSS'Z'",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{5}\\w$" to "yyyy-M-d'T'HH:mm:ss.SSSSS'Z'",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{4}\\w$" to "yyyy-M-d'T'HH:mm:ss.SSSS'Z'",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{3}\\w$" to "yyyy-M-d'T'HH:mm:ss.SSS'Z'",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{2}\\w$" to "yyyy-M-d'T'HH:mm:ss.SS'Z'",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1}\\w$" to "yyyy-M-d'T'HH:mm:ss.S'Z'",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\w$" to "yyyy-M-d'T'HH:mm:ss'Z'",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\d{0,6}$" to "yyyy-M-d'T'HH:mm:ss",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}\\w$" to "yyyy-M-d'T'HH:mm:ss'Z'",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\w\\d{1,2}:\\d{1,2}:\\d{1,2}$" to "yyyy-M-d'T'HH:mm:ss",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{0,6}\\d{0,6}$" to "yyyy-M-d HH:mm:ss.SSSSSS",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{0,6}\\w$" to "yyyy-M-d HH:mm:ss.SSS'Z'",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\d{0,6}$" to "yyyy-M-d HH:mm:ss",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\w$" to "yyyy-M-d HH:mm:ss'Z'",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}$" to "yyyy-M-d HH:mm:ss",
        "^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}$" to "yyyy/MM/dd HH:mm:ss",
        "^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$" to "dd-MM-yyyy HH:mm:ss",
        "^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$" to "MM/dd/yyyy HH:mm:ss",
        "^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$" to "dd MMM yyyy HH:mm:ss",
        "^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$" to "dd MMMM yyyy HH:mm:ss",
        "^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{1,2}$" to "dd-MM-yyyy HH:mm",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}$" to "yyyy-MM-dd HH:mm",
        "^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{1,2}$" to "MM/dd/yyyy HH:mm",
        "^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{1,2}$" to "yyyy/MM/dd HH:mm",
        "^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{1,2}$" to "dd MMM yyyy HH:mm",
        "^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{1,2}$" to "dd MMMM yyyy HH:mm",
        "^\\d{4}-\\d{1,2}-\\d{1,2}$" to "yyyy-MM-dd",
        "^\\d{4}/\\d{1,2}/\\d{1,2}$" to "yyyy/MM/dd",
        "^\\d{1,2}-\\d{1,2}-\\d{4}$" to "dd-MM-yyyy",
        "^\\d{1,2}/\\d{1,2}/\\d{4}$" to "MM/dd/yyyy",
        "^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$" to "dd MMM yyyy",
        "^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$" to "dd MMMM yyyy",
        "^\\d{8}\\s\\d{6}$" to "yyyyMMdd HHmmss",
        "^\\d{8}\\s\\d{4}$" to "yyyyMMdd HHmm",
        "^\\d{14}$" to "yyyyMMddHHmmss",
        "^\\d{12}$" to "yyyyMMddHHmm",
        "^\\d{8}$" to "yyyyMMdd"
    )
}