package com.thebluekernel.calendar.demo.utils

import android.content.Context
import android.view.View
import com.thebluekernel.calendar.demo.databinding.ItemDayLayoutBinding
import com.thebluekernel.calendar.demo.databinding.ItemMonthResLayoutBinding
import com.thebluekernel.calendar.library.data.model.CalendarDay
import com.thebluekernel.calendar.library.data.model.CalendarMonth
import com.thebluekernel.calendar.library.data.ui.CalendarViewHolder

/**
 * Created by Ahmed Ibrahim on 21,November,2021
 */

// Day class instance
class DayViewContainer(view: View) : CalendarViewHolder(view) {
    lateinit var day: CalendarDay
    val dayTextView = ItemDayLayoutBinding.bind(view).dayTextView
    val context: Context = dayTextView.context
}

// Month class instance
class MonthViewContainer(view: View) : CalendarViewHolder(view) {
    lateinit var month: CalendarMonth
    val binding = ItemMonthResLayoutBinding.bind(view)
}