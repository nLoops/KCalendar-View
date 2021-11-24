package com.thebluekernel.calendar.library.data.ui

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.thebluekernel.calendar.library.R
import com.thebluekernel.calendar.library.data.model.ARABIC_LOCALE
import com.thebluekernel.calendar.library.data.model.CalendarDay
import com.thebluekernel.calendar.library.data.model.DEFAULT_LOCALE
import com.thebluekernel.calendar.library.data.utils.daysOfWeekFromLocale
import com.thebluekernel.calendar.library.data.utils.inflate
import java.time.format.TextStyle
import java.util.*

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */

internal class WeekViewHolder(private val dayHolders: List<DayViewHolder>) {
    private lateinit var view: LinearLayout

    fun inflate(parent: LinearLayout,locale:String): View {
        view = LinearLayout(parent.context).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
            weightSum = dayHolders.count().toFloat()
            layoutDirection = when (locale) {
                ARABIC_LOCALE -> View.LAYOUT_DIRECTION_RTL
                DEFAULT_LOCALE -> View.LAYOUT_DIRECTION_LTR
                else -> View.LAYOUT_DIRECTION_LTR
            }
            for (holder in dayHolders) {
                addView(holder.inflate(this))
            }
        }
        return view
    }

    fun bind(daysOfWeek: List<CalendarDay>) {
        view.isGone = daysOfWeek.isEmpty()
        dayHolders.forEachIndexed { index, holder ->
            holder.bind(daysOfWeek.getOrNull(index))
        }
    }

    fun reloadDay(day: CalendarDay): Boolean = dayHolders.any { it.reloadViewIfNecessary(day) }
}