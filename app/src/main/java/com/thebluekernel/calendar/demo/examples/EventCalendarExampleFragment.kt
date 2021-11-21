package com.thebluekernel.calendar.demo.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.thebluekernel.calendar.demo.R
import com.thebluekernel.calendar.demo.databinding.FragmentEventExampleBinding
import com.thebluekernel.calendar.demo.utils.CalendarEventDayContainer
import com.thebluekernel.calendar.demo.utils.MonthViewContainer
import com.thebluekernel.calendar.library.data.model.CalendarDay
import com.thebluekernel.calendar.library.data.model.CalendarMonth
import com.thebluekernel.calendar.library.data.model.monthName
import com.thebluekernel.calendar.library.data.model.yearValue
import com.thebluekernel.calendar.library.data.ui.CalendarDayBinder
import com.thebluekernel.calendar.library.data.ui.CalendarMonthBinder
import java.time.LocalDate

/**
 * Created by Ahmed Ibrahim on 21,November,2021
 */
class EventCalendarExampleFragment : Fragment() {

    private lateinit var binding: FragmentEventExampleBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventExampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendarView()
    }

    private fun initCalendarView() = with(binding) {
        val monthBinder = object : CalendarMonthBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)

            override fun bind(holder: MonthViewContainer, month: CalendarMonth) {
                with(holder) {
                    this.month = month
                    with(binding) {
                        tvMonthName.text =
                            month.monthName().plus(" ").plus(month.yearValue())
                        btnArrowNext.setOnClickListener { calendarView.scrollToNext(month.month) }
                        btnArrowPrev.setOnClickListener { calendarView.scrollToPrev(month.month) }
                    }
                }
            }

        }
        val dayBinder = object : CalendarDayBinder<CalendarEventDayContainer> {
            override fun create(view: View): CalendarEventDayContainer =
                CalendarEventDayContainer(view)

            override fun bind(holder: CalendarEventDayContainer, day: CalendarDay) {
                with(holder) {
                    this.day = day
                    with(binding) {
                        val context = root.context
                        tvDay.text = day.getFormatted("dd")
                        tvDay.setBackgroundResource(0)
                        tvDay.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorCalendarTextSelected
                            )
                        )
                        val hasEvent = events.firstOrNull { date -> day.isEqualTo(date.eventDate) }
                        if (hasEvent != null) {
                            val color = when (hasEvent.type) {
                                CalendarEventType.PERSONAL -> R.drawable.bg_personl_event_calendar
                                CalendarEventType.GLOBAL -> R.drawable.bg_event_calendar
                            }
                            vEventBackground.background =
                                ResourcesCompat.getDrawable(resources, color, null)
                        }
                        vEventBackground.isVisible = hasEvent != null
                    }
                }
            }

        }
        calendarView.monthBinder = monthBinder
        calendarView.dayBinder = dayBinder
    }
}

val events = listOf(
    CalendarEvent(
        R.color.colorCalendarEvent,
        LocalDate.of(2021, 11, 25),
        CalendarEventType.GLOBAL,
        ""
    ),
    CalendarEvent(
        R.color.colorCalendarEvent,
        LocalDate.of(2021, 11, 1),
        CalendarEventType.PERSONAL,
        ""
    ),
    CalendarEvent(
        R.color.colorCalendarEvent,
        LocalDate.of(2021, 11, 29),
        CalendarEventType.GLOBAL,
        ""
    ),
    CalendarEvent(
        R.color.colorCalendarEvent,
        LocalDate.of(2021, 12, 25),
        CalendarEventType.PERSONAL,
        ""
    ), CalendarEvent(
        R.color.colorCalendarEvent,
        LocalDate.of(2021, 12, 13),
        CalendarEventType.GLOBAL,
        ""
    )
)

data class CalendarEvent(
    val colorCode: Int,
    val eventDate: LocalDate,
    val type: CalendarEventType,
    val data: String // json string to encapsulate what various
)

enum class CalendarEventType {
    PERSONAL,
    GLOBAL
}