package com.thebluekernel.calendar.library.data.utils

import java.time.YearMonth

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */

val YearMonth.next: YearMonth
    get() = this.plusMonths(1)

val YearMonth.previous: YearMonth
    get() = this.minusMonths(1)