package com.thebluekernel.calendar.library.data.ui.monthlist

import androidx.annotation.LayoutRes
import com.thebluekernel.calendar.library.data.model.Size
import com.thebluekernel.calendar.library.data.ui.CalendarDayBinder
import com.thebluekernel.calendar.library.data.ui.CalendarMonthBinder
import com.thebluekernel.calendar.library.data.ui.CalendarViewHolder

/**
 * Created by Ahmed Ibrahim on 16,November,2021
 */
internal data class BindingParams(
    val calendarSize: Size,
    @LayoutRes val dayViewRes: Int,
    val binder: CalendarDayBinder<CalendarViewHolder>,
    @LayoutRes val monthViewRes: Int,
    val monthBinder: CalendarMonthBinder<CalendarViewHolder>
)