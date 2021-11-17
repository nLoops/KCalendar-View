package com.thebluekernel.calendar.library.data.ui.monthlist

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.thebluekernel.calendar.library.data.model.CalendarDay
import com.thebluekernel.calendar.library.data.utils.NO_INDEX
import java.time.YearMonth

/**
 * Created by Ahmed Ibrahim on 31,October,2021
 */
internal class KCalendarLayoutManager(
    private val kCalendarView: KCalendarView,
    @RecyclerView.Orientation orientation: Int
) : LinearLayoutManager(kCalendarView.context, orientation, false) {

    private val adapter: KCalendarViewAdapter
        get() = kCalendarView.adapter as KCalendarViewAdapter

    fun scrollToMonth(month: YearMonth) {
        val position = adapter.getAdapterPosition(month)
        if (position == NO_INDEX) return
        scrollToPositionWithOffset(position, 0)
//        calView.post { adapter.notifyMonthScrollListenerIfNeeded() }
    }

    fun smoothScrollToMonth(month: YearMonth) {
        val position = adapter.getAdapterPosition(month)
        if (position == NO_INDEX) return
        startSmoothScroll(CalendarSmoothScroller(position, null))
    }

    private fun calculateDayViewOffsetInParent(day: CalendarDay, itemView: View): Int {
        val dayView = itemView.findViewWithTag<View>(day.date.hashCode()) ?: return 0
        val rect = Rect()
        dayView.getDrawingRect(rect)
        (itemView as ViewGroup).offsetDescendantRectToMyCoords(dayView, rect)
        return if (kCalendarView.isVertical) rect.top + kCalendarView.monthMarginTop else rect.left + kCalendarView.monthMarginStart
    }

    private inner class CalendarSmoothScroller(position: Int, val day: CalendarDay?) :
        LinearSmoothScroller(kCalendarView.context) {

        init {
            targetPosition = position
        }

        override fun getVerticalSnapPreference(): Int = SNAP_TO_START

        override fun getHorizontalSnapPreference(): Int = SNAP_TO_START

        override fun calculateDyToMakeVisible(view: View, snapPreference: Int): Int {
            val dy = super.calculateDyToMakeVisible(view, snapPreference)
            if (day == null) {
                return dy
            }
            val offset = calculateDayViewOffsetInParent(day, view)
            return dy - offset
        }

        override fun calculateDxToMakeVisible(view: View, snapPreference: Int): Int {
            val dx = super.calculateDxToMakeVisible(view, snapPreference)
            if (day == null) {
                return dx
            }
            val offset = calculateDayViewOffsetInParent(day, view)
            return dx - offset
        }
    }
}