package com.thebluekernel.calendar.library.data.ui

import android.view.View
import com.thebluekernel.calendar.library.data.model.CalendarDay
import com.thebluekernel.calendar.library.data.model.CalendarMonth

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */

/**
 * Wrapper class to contains [View]
 */
open class CalendarViewHolder(val view: View)

/**
 * to achieve maximum flexibility instead of create one view and let the user change it style
 * we let the user create and bind the view itself.
 */
interface CalendarDayBinder<T : CalendarViewHolder> {
    fun create(view: View): T
    fun bind(holder: T, day: CalendarDay)
}

interface CalendarMonthBinder<T : CalendarViewHolder> {
    fun create(view: View): T
    fun bind(holder: T, month: CalendarMonth)
}