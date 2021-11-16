package com.thebluekernel.calendar.library.data.ui.monthlist

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thebluekernel.calendar.library.data.utils.NO_INDEX
import java.time.YearMonth

/**
 * Created by Ahmed Ibrahim on 31,October,2021
 */
internal class MonthListLayoutManager(
    private val monthList: CalendarMonthList,
    @RecyclerView.Orientation orientation: Int
) : LinearLayoutManager(monthList.context, orientation, false) {

    private val adapter: MonthListAdapter
        get() = monthList.adapter as MonthListAdapter

    fun scrollToMonth(month: YearMonth) {
        val position = adapter.getAdapterPosition(month)
        if (position == NO_INDEX) return
        scrollToPositionWithOffset(position, 0)
//        calView.post { adapter.notifyMonthScrollListenerIfNeeded() }
    }

//    fun smoothScrollToMonth(month: YearMonth) {
//        val position = adapter.getAdapterPosition(month)
//        if (position == NO_INDEX) return
//        startSmoothScroll(CalendarSmoothScroller(position, null))
//    }
}