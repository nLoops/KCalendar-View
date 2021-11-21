package com.thebluekernel.calendar.demo.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Created by Ahmed Ibrahim on 21,November,2021
 */
fun LocalDate.to(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(this)
}