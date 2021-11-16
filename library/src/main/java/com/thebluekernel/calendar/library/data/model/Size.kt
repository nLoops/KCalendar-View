package com.thebluekernel.calendar.library.data.model

import androidx.annotation.Px
import com.thebluekernel.calendar.library.data.ui.monthlist.CalendarMonthList

/**
 * Created by Ahmed Ibrahim on 27,October,2021
 *
 * Holder for width and height like Android Size but can use
 * Kotlin data class copy to update values upon onMeasure
 *
 * @see CalendarMonthList
 */
data class Size(@Px val width: Int, @Px val height: Int)