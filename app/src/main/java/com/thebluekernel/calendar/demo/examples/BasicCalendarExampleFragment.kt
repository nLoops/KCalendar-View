package com.thebluekernel.calendar.demo.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.thebluekernel.calendar.demo.R
import com.thebluekernel.calendar.demo.databinding.FragmentBasicExampleBinding
import com.thebluekernel.calendar.demo.utils.Constants.ARABIC_LOCALE
import com.thebluekernel.calendar.demo.utils.Constants.ENGLISH_LOCALE
import com.thebluekernel.calendar.demo.utils.DayViewContainer
import com.thebluekernel.calendar.demo.utils.MonthViewContainer
import com.thebluekernel.calendar.library.data.model.CalendarDay
import com.thebluekernel.calendar.library.data.model.CalendarMonth
import com.thebluekernel.calendar.library.data.model.monthName
import com.thebluekernel.calendar.library.data.model.yearValue
import com.thebluekernel.calendar.library.data.ui.CalendarDayBinder
import com.thebluekernel.calendar.library.data.ui.CalendarMonthBinder

/**
 * Created by Ahmed Ibrahim on 21,November,2021
 */
class BasicCalendarExampleFragment : Fragment() {

    private lateinit var binding: FragmentBasicExampleBinding

    private var selectedDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasicExampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendarView()
        setOnClickListener()
    }

    private fun initCalendarView() = with(binding) {
        val dayBinder = object : CalendarDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(holder: DayViewContainer, day: CalendarDay) {
                with(holder) {
                    this.day = day
                    val formattedDate = day.getFormatted(DEFAULT_DATE_PATTERN)
                    dayTextView.text = day.getFormatted("d")
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
                            calendarView.getMonthName(month).plus(" ").plus(month.yearValue())
                        btnArrowNext.setOnClickListener { calendarView.scrollToNext(month.month) }
                        btnArrowPrev.setOnClickListener { calendarView.scrollToPrev(month.month) }
                    }
                }
            }

        }
        calendarView.dayBinder = dayBinder
        calendarView.monthBinder = monthBinder
    }

    private fun setOnClickListener() = with(binding) {
        btnArabic.setOnClickListener { calendarView.calendarLocale = ARABIC_LOCALE }
        btnEnglish.setOnClickListener { calendarView.calendarLocale = ENGLISH_LOCALE }
    }

    companion object {
        private const val DEFAULT_DATE_PATTERN = "yyyy-MM-dd"
    }
}