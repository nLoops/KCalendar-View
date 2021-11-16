package com.thebluekernel.calendar.library.data.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thebluekernel.calendar.library.data.model.CalendarDay
import com.thebluekernel.calendar.library.data.model.CalendarMonth
import com.thebluekernel.calendar.library.data.ui.monthlist.MonthListAdapter

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */
internal class MonthViewHolder constructor(
    rootLayout: ViewGroup,
    private val adapter: MonthListAdapter,
    private val weekHolders: List<WeekViewHolder>
) : RecyclerView.ViewHolder(rootLayout) {

    val headerView: View? = rootLayout.findViewById(adapter.monthHeaderViewID)
    private var headerContainer: CalendarViewHolder? = null

    lateinit var currentMonth: CalendarMonth

    fun bind(month: CalendarMonth) {
        this.currentMonth = month
        headerView?.let { v ->
            val binder = adapter.dayParams.monthBinder
            val headerContainer = headerContainer ?: binder.create(v).also {
                headerContainer = it
            }
            binder.bind(headerContainer, month)
        }
        weekHolders.forEachIndexed { index, weekViewHolder ->
            weekViewHolder.bind(month.days.getOrNull(index).orEmpty())
        }
    }

    fun reloadDay(day: CalendarDay) {
        weekHolders.find { it.reloadDay(day) }
    }
}