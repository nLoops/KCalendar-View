package com.thebluekernel.calendar.library.data.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thebluekernel.calendar.library.data.model.CalendarMonth

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */
internal class MonthViewHolder constructor(
    rootLayout: ViewGroup,
    private val weekHolders: List<WeekViewHolder>
) : RecyclerView.ViewHolder(rootLayout) {

    lateinit var currentMonth: CalendarMonth

    fun bind(month: CalendarMonth) {
        this.currentMonth = month
        weekHolders.forEachIndexed { index, weekViewHolder ->
            weekViewHolder.bind(month.days.getOrNull(index).orEmpty())
        }
    }
}