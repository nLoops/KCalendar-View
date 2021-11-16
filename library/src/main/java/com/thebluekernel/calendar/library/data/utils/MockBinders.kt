package com.thebluekernel.calendar.library.data.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.thebluekernel.calendar.library.data.model.CalendarDay
import com.thebluekernel.calendar.library.data.model.CalendarMonth
import com.thebluekernel.calendar.library.data.ui.CalendarDayBinder
import com.thebluekernel.calendar.library.data.ui.CalendarMonthBinder
import com.thebluekernel.calendar.library.data.ui.CalendarViewHolder
import com.thebluekernel.calendar.library.databinding.ItemDayLayoutBinding
import com.thebluekernel.calendar.library.databinding.ItemMonthResLayoutBinding
import java.time.DayOfWeek
import java.time.YearMonth

/**
 * Created by Ahmed Ibrahim on 31,October,2021
 */
class DayViewContainer(view: View) : CalendarViewHolder(view) {
    lateinit var day: CalendarDay
    val dayTextView = ItemDayLayoutBinding.bind(view).dayTextView

    init {
        dayTextView.setOnClickListener {
            Snackbar.make(
                view,
                "You've clicked on day ${day.getFormatted("yyyy-MM-dd")}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}

val mockDayBinder = object : CalendarDayBinder<DayViewContainer> {
    override fun create(view: View) = DayViewContainer(view)

    override fun bind(holder: DayViewContainer, day: CalendarDay) {
        with(holder) {
            this.day = day
            dayTextView.text = day.date.dayOfMonth.toString()
        }
    }

}

class MonthViewContainer(view: View) : CalendarViewHolder(view) {
    lateinit var month: CalendarMonth
    val monthTextView = ItemMonthResLayoutBinding.bind(view).root

    init {
        monthTextView.setOnClickListener {
            Snackbar.make(view, "Why clicked on month", Snackbar.LENGTH_SHORT).show()
        }
    }
}

val mockMonthBinder = object : CalendarMonthBinder<MonthViewContainer> {
    override fun create(view: View) = MonthViewContainer(view)

    override fun bind(holder: MonthViewContainer, month: CalendarMonth) {
        with(holder) {
            this.month = month
            monthTextView.text = month.getMonthName().plus(" ").plus(month.getYear())
        }
    }

}
