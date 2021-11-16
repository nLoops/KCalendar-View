package com.thebluekernel.calendar.library.data.ui.monthlist

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Ahmed Ibrahim on 31,October,2021
 *
 * Helper to allow [CalendarMonthList] scroll like pager in H state
 *
 */
internal class CalenderPageSnapHelper : PagerSnapHelper() {
    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        return IntArray(2).apply {
            this[0] = if (layoutManager.canScrollHorizontally())
                distanceToStart(targetView, getHorizontalHelper(layoutManager)) else 0

            this[1] = if (layoutManager.canScrollVertically())
                distanceToStart(targetView, getVerticalHelper(layoutManager)) else 0
        }
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
        val childStart = (helper.getDecoratedStart(targetView))
        val containerStart = helper.startAfterPadding
        return childStart - containerStart
    }

    private lateinit var verticalHelper: OrientationHelper
    private lateinit var horizontalHelper: OrientationHelper

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (!::verticalHelper.isInitialized || verticalHelper.layoutManager != layoutManager) {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return verticalHelper
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (!::horizontalHelper.isInitialized || horizontalHelper.layoutManager != layoutManager) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return horizontalHelper
    }
}