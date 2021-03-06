package com.thebluekernel.calendar.library.data.ui.monthlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.paris.extensions.style
import com.thebluekernel.calendar.library.R
import com.thebluekernel.calendar.library.data.model.*
import com.thebluekernel.calendar.library.data.model.CalendarRangeCreator
import com.thebluekernel.calendar.library.data.ui.*
import com.thebluekernel.calendar.library.data.utils.NO_INDEX
import com.thebluekernel.calendar.library.data.utils.daysOfWeekFromLocale
import com.thebluekernel.calendar.library.data.utils.inflate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

/**
 * Created by Ahmed Ibrahim on 31,October,2021
 */
internal class KCalendarViewAdapter(
    private val monthList: KCalendarView,
    private val creator: CalendarRangeCreator
) : RecyclerView.Adapter<MonthViewHolder>() {

    private val months: List<CalendarMonth>
        get() = creator.months

    val dayParams by lazy {
        with(monthList) {
            BindingParams(
                dayItemSize,
                dayViewRes,
                dayBinder as CalendarDayBinder<CalendarViewHolder>,
                monthViewRes,
                monthBinder as CalendarMonthBinder<CalendarViewHolder>
            )
        }
    }

    var monthHeaderViewID = ViewCompat.generateViewId()

    init {
        setHasStableIds(true)
    }

    private fun getMonth(position: Int): CalendarMonth = months[position]

    override fun getItemId(position: Int) = getMonth(position).hashCode().toLong()

    override fun getItemCount() = months.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val context = parent.context
        val locale = monthList.calendarLocale
        val rootLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }

        // inflate month header
        attachMonthHeaderView(rootLayout)

        // inflate week days
        attachWeekDaysRow(context, rootLayout, locale)

        // inflate days of month grouped by week
        val weeksViewHolders = attachMonthDaysRows(rootLayout, locale)

        return MonthViewHolder(rootLayout, this, weeksViewHolders)

    }

    private fun attachMonthDaysRows(
        rootLayout: LinearLayout,
        locale: String
    ): List<WeekViewHolder> {
        val weeksViewHolders = (1..6)
            .map { WeekViewHolder(generateWeekDayHolders()) }
            .onEach { weekViewHolder ->
                rootLayout.addView(
                    weekViewHolder.inflate(
                        rootLayout,
                        locale
                    )
                )
            }
        return weeksViewHolders
    }

    private fun attachMonthHeaderView(rootLayout: LinearLayout) {
        if (dayParams.monthViewRes != 0) {
            val monthHeaderView = rootLayout.inflate(dayParams.monthViewRes)
            // Don't overwrite ID set by the user.
            if (monthHeaderView.id == View.NO_ID) {
                monthHeaderView.id = monthHeaderViewID
            } else {
                monthHeaderViewID = monthHeaderView.id
            }
            rootLayout.addView(monthHeaderView)
        }
    }

    private fun attachWeekDaysRow(
        context: Context?,
        rootLayout: LinearLayout,
        locale: String
    ) {
        val weekDaysHolder =
            LayoutInflater.from(context).inflate(R.layout.item_week_layout, rootLayout, false)
        val legendLayout = weekDaysHolder.findViewById<LinearLayout>(R.id.legendLayout)
        val daysOfWeek = daysOfWeekFromLocale(monthList.firstDayOfWeek)
        legendLayout.children.forEachIndexed { index, view ->
            val textValue = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale(monthList.calendarLocale))
            (view as TextView).apply {
                text = textValue.uppercase(Locale.getDefault())
                style(monthList.weekTextStyle)
            }
        }
        legendLayout.layoutDirection = when (locale) {
            ARABIC_LOCALE -> View.LAYOUT_DIRECTION_RTL
            DEFAULT_LOCALE -> View.LAYOUT_DIRECTION_LTR
            else -> View.LAYOUT_DIRECTION_LTR
        }
        rootLayout.addView(legendLayout)
    }

    private fun generateWeekDayHolders() = (1..7).map { DayViewHolder(dayParams) }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.forEach {
                holder.reloadDay(it as CalendarDay)
            }
        }
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(getMonth(position))
    }

    internal fun getAdapterPosition(month: YearMonth): Int {
        return months.indexOfFirst { it.month == month }
    }

    private fun getAdapterPosition(day: CalendarDay): Int = months.indexOfFirst { months ->
        months.days.any { weeks -> weeks.any { it == day } }
    }

    fun reloadMonth(month: YearMonth) {
        notifyItemChanged(getAdapterPosition(month))
    }

    fun reloadCalendar() {
        notifyItemRangeChanged(0, itemCount)
    }

    fun reloadDay(day: CalendarDay) {
        val position = getAdapterPosition(day)
        if (position != NO_INDEX) {
            notifyItemChanged(position, day)
        }
    }
}