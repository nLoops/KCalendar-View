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

    private var isScrollEnabled = true

    private val adapter: KCalendarViewAdapter
        get() = kCalendarView.adapter as KCalendarViewAdapter

    override fun canScrollHorizontally(): Boolean {
        return isScrollEnabled && super.canScrollHorizontally()
    }

    internal fun setScrollEnabled(isEnabled: Boolean) {
        isScrollEnabled = isEnabled
    }

    fun scrollToMonth(month: YearMonth) {
        val position = adapter.getAdapterPosition(month)
        if (position == NO_INDEX) return
        scrollToPositionWithOffset(position, 0)
    }

    fun smoothScrollToMonth(month: YearMonth, onFinished: () -> Unit) {
        val position = adapter.getAdapterPosition(month)
        if (position == NO_INDEX) return
        startSmoothScroll(CalendarSmoothScroller(position, null, onFinished))
    }

    private fun calculateDayViewOffsetInParent(day: CalendarDay, itemView: View): Int {
        val dayView = itemView.findViewWithTag<View>(day.date.hashCode()) ?: return 0
        val rect = Rect()
        dayView.getDrawingRect(rect)
        (itemView as ViewGroup).offsetDescendantRectToMyCoords(dayView, rect)
        return if (kCalendarView.isVertical) rect.top else rect.left
    }

    private inner class CalendarSmoothScroller(
        position: Int, val day: CalendarDay?,
        val onFinished: () -> Unit
    ) :
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

        override fun onStop() {
            super.onStop()
            onFinished.invoke()
        }
    }
}