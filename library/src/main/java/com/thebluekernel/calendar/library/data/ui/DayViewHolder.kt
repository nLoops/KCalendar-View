package com.thebluekernel.calendar.library.data.ui

import android.util.Size
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.thebluekernel.calendar.library.data.model.CalendarDay
import com.thebluekernel.calendar.library.data.utils.inflate

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */
internal data class DayParams(
    val calendarSize: Size,
    @LayoutRes val layoutId: Int,
    val binder: CalendarDayBinder<CalendarViewHolder>
)

internal class DayViewHolder(private val params: DayParams) {

    private lateinit var view: View
    private lateinit var holder: CalendarViewHolder
    private var calendarDay: CalendarDay? = null

    fun inflate(parent: LinearLayout): View {
        view = parent.inflate(params.layoutId).apply {
            // using [LinearLayout] to arrange days to fill whole width
            // equally cause each row holds 7 days or represent a week
            updateLayoutParams<LinearLayout.LayoutParams> {
                width = params.calendarSize.width - marginStart - marginEnd
                height = params.calendarSize.height - marginTop - marginBottom
                weight = 1f
            }
        }
        return view
    }

    fun bind(currentDay: CalendarDay?) {
        this.calendarDay = currentDay

        if (!::holder.isInitialized) {
            holder = params.binder.create(view)
        }

        if (currentDay != null) {
            holder.view.isVisible = true
            params.binder.bind(holder, currentDay)
        } else {
            holder.view.isVisible = false
        }
    }

}