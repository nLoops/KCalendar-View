package com.thebluekernel.calendar.library.data.ui

import android.view.View
import com.thebluekernel.calendar.library.data.model.CalendarDay

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */

open class CalendarViewHolder(val view: View)

interface CalendarDayBinder<T : CalendarViewHolder> {
    fun create(view: View): T
    fun bind(holder: T, day: CalendarDay)
}