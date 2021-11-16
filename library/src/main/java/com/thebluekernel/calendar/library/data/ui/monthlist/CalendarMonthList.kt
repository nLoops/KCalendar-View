package com.thebluekernel.calendar.library.data.ui.monthlist

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import androidx.recyclerview.widget.RecyclerView
import com.thebluekernel.calendar.library.R
import com.thebluekernel.calendar.library.data.model.CalendarRangeCreator
import com.thebluekernel.calendar.library.data.model.CalendarType
import com.thebluekernel.calendar.library.data.model.DEFAULT_LOCALE
import com.thebluekernel.calendar.library.data.model.Size
import com.thebluekernel.calendar.library.data.ui.CalendarDayBinder
import com.thebluekernel.calendar.library.data.ui.CalendarMonthBinder
import com.thebluekernel.calendar.library.data.utils.mockDayBinder
import com.thebluekernel.calendar.library.data.utils.mockMonthBinder
import com.thebluekernel.calendar.library.data.utils.todayInHijri
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.chrono.HijrahDate

/**
 * Created by Ahmed Ibrahim on 31,October,2021
 */
open class CalendarMonthList @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    /**
     *  day binder which tells library two info
     *
     * first how to create the day cell
     * two how to bind it
     *
     * @see [CalendarDayBinder]
     */
    var dayBinder: CalendarDayBinder<*>? = null
        set(value) {
            field = value
            invalidateViewHolders()
        }

    /**
     * layout resource id which passed in xml attrs to represent day cell view
     */
    var dayViewRes = 0
        set(value) {
            if (field != value) {
                if (value == 0) throw IllegalArgumentException("Day resource cannot be null.")
                field = value
                updateAdapterViewConfig()
            }
        }

    /**
     * month binder which tells library two info
     *
     * first how to create the month header
     * two how to bind it
     *
     * @see [CalendarMonthBinder]
     */
    var monthBinder: CalendarMonthBinder<*>? = null
        set(value) {
            field = value
            invalidateViewHolders()
        }

    /**
     * layout resource id which passed in xml attrs to represent day cell view
     */
    var monthViewRes = 0
        set(value) {
            if (field != value) {
                if (value == 0) throw IllegalArgumentException("Month resource cannot be null.")
                field = value
                updateAdapterViewConfig()
            }
        }

    @Orientation
    var orientation = HORIZONTAL
        set(value) {
            if (field != value) {
                field = value
                setup()
            }
        }

    var calendarType = CalendarType.GREGORIAN
        set(value) {
            if (field != value) {
                field = value
                setup()
            }
        }

    var calendarLocale = DEFAULT_LOCALE
        set(value) {
            if (field != value) {
                field = value
                // TODO: 02/11/2021 need to re-render calendar.
            }
        }

    /**
     * min year of calendar can be passed via XML
     */
    var calendarMinYear = Year.now().minusYears(1).value
        set(value) {
            if (field != value) {
                field = value
                /// TODO: 02/11/2021 need to re-render the calendar.
            }
        }

    /**
     * max year of calendar can be passed via XML
     */
    var calendarMaxYear = Year.now().plusYears(1).value
        set(value) {
            if (field != value) {
                field = value
                /// TODO: 02/11/2021 need to re-render the calendar.
            }
        }

    var firstDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY
        set(value) {
            if (field != value) {
                field = value
                // TODO: 02/11/2021 re-render it.
            }
        }

    var dayItemSize: Size = SIZE_SQUARE
        set(value) {
            if (field != value) {
                field = value
                setup()
            }
        }

    /// TODO: 02/11/2021 need to reactive to min/max year
    var startMonth: YearMonth = YearMonth.of(calendarMinYear, 1)
    var endMonth: YearMonth = YearMonth.of(calendarMaxYear, 12)

    private val calendarLayoutManager: MonthListLayoutManager
        get() = layoutManager as MonthListLayoutManager

    private val calendarAdapter: MonthListAdapter
        get() = adapter as MonthListAdapter

    private val pagerSnapHelper = CalenderPageSnapHelper()

    init {
        dayBinder = mockDayBinder
        monthBinder = mockMonthBinder
        initWithStyledAttributes()
    }

    private fun initWithStyledAttributes() {
        if (isInEditMode) return
        setHasFixedSize(true)
        context.withStyledAttributes(
            attrs,
            R.styleable.CalendarMonthList,
            defStyleAttr,
            defStyleAttr
        ) {
            dayViewRes = getResourceId(R.styleable.CalendarMonthList_cal_dayViewRes, dayViewRes)
            monthViewRes =
                getResourceId(R.styleable.CalendarMonthList_cal_monthViewRes, monthViewRes)
            orientation = getInt(R.styleable.CalendarMonthList_cal_scrollDirection, orientation)
            calendarLocale = getString(R.styleable.CalendarMonthList_cal_locale) ?: calendarLocale
            calendarType = CalendarType.values()[getInt(
                R.styleable.CalendarMonthList_cal_calendarType,
                calendarType.ordinal
            )]
            calendarMinYear =
                getInt(R.styleable.CalendarMonthList_cal_calendarMinYear, calendarMinYear)
            calendarMaxYear =
                getInt(R.styleable.CalendarMonthList_cal_calendarMaxYear, calendarMaxYear)
            firstDayOfWeek = DayOfWeek.values()[getInt(
                R.styleable.CalendarMonthList_cal_calendarStartOfWeek,
                firstDayOfWeek.ordinal
            )]
        }
        check(dayViewRes != 0) { "dayViewRes not provided" }
        check(monthViewRes != 0) { "monthViewRes not provided" }
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        if (!isInEditMode) {
            val widthMode = MeasureSpec.getMode(widthSpec)
            val widthSize = MeasureSpec.getSize(widthSpec)
            val heightMode = MeasureSpec.getMode(heightSpec)
            if (widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED) {
                throw UnsupportedOperationException("Cannot calculate the values for day Width/Height with the current configuration.")
            }
            // +0.5 => round to the nearest pixel
            val size = ((widthSize / 7f) + 0.5).toInt()
            val newSize = dayItemSize.copy(width = size, height = size)
            if (dayItemSize != newSize) {
                dayItemSize = newSize
            }
        }
        super.onMeasure(widthSpec, heightSpec)
    }

    private fun invalidateViewHolders() {
        if (adapter == null || layoutManager == null) return
        val state = layoutManager?.onSaveInstanceState()
        adapter = adapter
        layoutManager?.onRestoreInstanceState(state)
    }

    private fun updateAdapterViewConfig() {
        if (adapter != null) {
//            calendarAdapter.viewConfig =
//                ViewConfig(dayViewResource, monthHeaderResource, monthFooterResource, monthViewClass)
            invalidateViewHolders()
        }
    }

    fun setup() {
        val isHijri = when (calendarType) {
            CalendarType.HIJRI -> true
            CalendarType.GREGORIAN -> false
            else -> false
        }
        val calendarCreator = CalendarRangeCreator(startMonth, endMonth, firstDayOfWeek, isHijri)
        adapter = MonthListAdapter(this, calendarCreator)
        if (orientation == HORIZONTAL) pagerSnapHelper.attachToRecyclerView(this)
        layoutManager = MonthListLayoutManager(this, orientation)
            .apply { scrollToMonth(YearMonth.now()) }
    }

    companion object {
        private const val SQUARE = Int.MIN_VALUE
        val SIZE_SQUARE = Size(SQUARE, SQUARE)
    }
}