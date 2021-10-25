package com.thebluekernel.calendar.library.data.ui

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isGone
import com.thebluekernel.calendar.library.data.model.CalendarDay

/**
 * Created by Ahmed Ibrahim on 24,October,2021
 */

internal class WeekViewHolder(private val dayHolders: List<DayViewHolder>) {
    private lateinit var view: LinearLayout

    fun inflate(parent: LinearLayout): View {
        view = LinearLayout(parent.context).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
            weightSum = dayHolders.count().toFloat()
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
}