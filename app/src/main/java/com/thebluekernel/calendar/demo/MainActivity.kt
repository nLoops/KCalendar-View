package com.thebluekernel.calendar.demo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.thebluekernel.calendar.demo.databinding.ActivityMainBinding
import com.thebluekernel.calendar.demo.databinding.ItemDayLayoutBinding
import com.thebluekernel.calendar.demo.databinding.ItemMonthResLayoutBinding
import com.thebluekernel.calendar.library.data.model.*
import com.thebluekernel.calendar.library.data.ui.CalendarDayBinder
import com.thebluekernel.calendar.library.data.ui.CalendarMonthBinder
import com.thebluekernel.calendar.library.data.ui.CalendarViewHolder
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCalendarView()
    }

    private fun initCalendarView() = with(binding) {
        val dayBinder = object : CalendarDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(holder: DayViewContainer, day: CalendarDay) {
                with(holder) {
                    this.day = day
                    val formattedDate = day.getFormatted(DEFAULT_DATE_PATTERN)
                    dayTextView.text = day.getFormatted("dd")
                    dayTextView.setOnClickListener {
                        selectedDate = formattedDate
                        calendarView.notifyMonthChanged(day)
                    }
                    with(day) {
                        when {
                            isBeforeToday() -> {
                                dayTextView.isEnabled = false
                                dayTextView.setBackgroundResource(0)
                                dayTextView.setTextColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.colorCalendarTextUnselected
                                    )
                                )
                            }
                            isAfterToday() -> {
                                dayTextView.setBackgroundResource(0)
                                dayTextView.setTextColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.colorCalendarTextSelected
                                    )
                                )
                            }
                            isEqualToday() -> {
                                dayTextView.setBackgroundResource(0)
                                dayTextView.setTextColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.colorCalendarTextToday
                                    )
                                )
                            }
                        }
                    }
                    if (selectedDate == formattedDate) {
                        dayTextView.setBackgroundResource(R.drawable.bg_calendar_today)
                        dayTextView.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorWhite
                            )
                        )
                    }
                }
            }

        }
        val monthBinder = object : CalendarMonthBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)

            override fun bind(holder: MonthViewContainer, month: CalendarMonth) {
                with(holder) {
                    this.month = month
                    with(binding) {
                        tvMonthName.text =
                            month.monthName(Locale("ar")).plus(" ").plus(month.yearValue())
                        btnArrowNext.setOnClickListener { calendarView.scrollToNext(month.month) }
                        btnArrowPrev.setOnClickListener { calendarView.scrollToPrev(month.month) }
                    }
                }
            }

        }
        calendarView.dayBinder = dayBinder
        calendarView.monthBinder = monthBinder
    }

    companion object {
        private const val DEFAULT_DATE_PATTERN = "yyyy-MM-dd"
    }
}

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