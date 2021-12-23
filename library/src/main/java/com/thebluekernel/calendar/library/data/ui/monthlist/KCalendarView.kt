package com.thebluekernel.calendar.library.data.ui.monthlist

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Px
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.recyclerview.widget.RecyclerView
import com.thebluekernel.calendar.library.R
import com.thebluekernel.calendar.library.data.model.*
import com.thebluekernel.calendar.library.data.ui.CalendarDayBinder
import com.thebluekernel.calendar.library.data.ui.CalendarMonthBinder
import com.thebluekernel.calendar.library.data.utils.*
import java.time.DayOfWeek
import java.time.Year
import java.time.YearMonth
import java.util.*

/**
 * Created by Ahmed Ibrahim on 31,October,2021
 */
open class KCalendarView @JvmOverloads constructor(
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
            render()
        }

    /**
     * layout resource id which passed in xml attrs to represent day cell view
     */
    var dayViewRes = 0
        set(value) {
            if (field != value) {
                if (value == 0) throw IllegalArgumentException("Day resource cannot be null.")
                field = value
                render()
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
            render()
        }

    /**
     * layout resource id which passed in xml attrs to represent day cell view
     */
    var monthViewRes = 0
        set(value) {
            if (field != value) {
                if (value == 0) throw IllegalArgumentException("Month resource cannot be null.")
                field = value
                render()
            }
        }

    /** style resource of week day text **/
    @StyleRes
    var weekTextStyle = R.style.Calendar_Widget_Week_Text_Style
        set(value) {
            if (field != value) {
                if (value == 0) throw IllegalArgumentException("Week text style cannot be null.")
                field = value
                render()
            }
        }

    @Orientation
    var orientation = HORIZONTAL
        set(value) {
            if (field != value) {
                field = value
                render()
            }
        }

    var calendarType = CalendarType.GREGORIAN
        set(value) {
            if (field != value) {
                field = value
                render()
            }
        }

    var calendarLocale = DEFAULT_LOCALE
        set(value) {
            if (field != value) {
                field = value
                render()
            }
        }

    /**
     * min year of calendar can be passed via XML
     *
     * default is one year before now
     */
    var calendarMinYear = Year.now().minusYears(1).value
        set(value) {
            if (field != value) {
                field = value
                render()
            }
        }

    /**
     * max year of calendar can be passed via XML
     *
     * default is one year after now
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
                render()
            }
        }

    /**
     * the size of day cell by default it's square width = height
     */
    var dayItemSize: Size = SIZE_SQUARE
        set(value) {
            if (field != value) {
                field = value
                render()
            }
        }

    var enableCalendarSwipe = true
        set(value) {
            if (field != value) {
                field = value
                render()
            }
        }

    private val calendarLayoutManager: KCalendarLayoutManager
        get() = layoutManager as KCalendarLayoutManager

    private val calendarAdapter: KCalendarViewAdapter
        get() = adapter as KCalendarViewAdapter

    private val pagerSnapHelper = KCalenderViewSnapHelper()

    /**
     * The margin, in pixels to be applied
     * to the start of each month view.
     */
    @Px
    var monthMarginStart = 0
        private set

    /**
     * The margin, in pixels to be applied
     * to the end of each month view.
     */
    @Px
    var monthMarginEnd = 0
        private set

    /**
     * The margin, in pixels to be applied
     * to the top of each month view.
     */
    @Px
    var monthMarginTop = 0
        private set

    /**
     * The margin, in pixels to be applied
     * to the bottom of each month view.
     */
    @Px
    var monthMarginBottom = 0
        private set

    internal val isVertical: Boolean
        get() = orientation == VERTICAL

    private val isHijri: Boolean
        get() = when (calendarType) {
            CalendarType.GREGORIAN -> false
            CalendarType.HIJRI -> true
        }

    init {
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
            weekTextStyle = getResourceId(
                R.styleable.CalendarMonthList_cal_WeekTextStyle,
                weekTextStyle
            )
            enableCalendarSwipe = getBoolean(
                R.styleable.CalendarMonthList_cal_enableCalendarSwipe,
                enableCalendarSwipe
            )
        }
        check(dayViewRes != 0) { "dayViewRes not provided" }
        check(monthViewRes != 0) { "monthViewRes not provided" }
        check(weekTextStyle != 0) { "week text style not provided" }
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
            val size = ((widthSize / WEEK_DAYS.toFloat()) + 0.5).toInt()
            val newSize = dayItemSize.copy(width = size, height = size)
            if (dayItemSize != newSize) {
                dayItemSize = newSize
            }
        }
        super.onMeasure(widthSpec, heightSpec)
    }

    // TODO: 21/11/2021 for better performance if already we have adapter just notify data changed
    // instead of create new instance.
    private fun render() {
        setListAdapter()
        setListScrollHelper()
        setListLayoutManager()
    }

    private fun setListLayoutManager() {
        val month = when (isHijri) {
            true -> YearMonth.now().next
            else -> YearMonth.now()
        }
        layoutManager = KCalendarLayoutManager(this, orientation)
            .apply {
                setScrollEnabled(enableCalendarSwipe)
                scrollToMonth(month)
            }
    }

    private fun setListScrollHelper() {
        if (orientation == HORIZONTAL) pagerSnapHelper.attachToRecyclerView(this)
    }

    private fun setListAdapter() {
        val calendarCreator = getCalendarCreator(isHijri)
        adapter = KCalendarViewAdapter(this, calendarCreator)
    }

    private fun getCalendarCreator(isHijri: Boolean): CalendarRangeCreator {
        val startMonth = YearMonth.of(calendarMinYear, FIRST_MONTH_OF_YEAR_INDEX)
        val endMonth = YearMonth.of(calendarMaxYear, LAST_MONTH_OF_YEAR_INDEX)
        return CalendarRangeCreator(startMonth, endMonth, firstDayOfWeek, isHijri)
    }


    // region PUBLIC METHODS
    fun scrollTo(month: YearMonth) = with(calendarLayoutManager) {
        setScrollEnabled(true)
        calendarLayoutManager.smoothScrollToMonth(month){
            setScrollEnabled(enableCalendarSwipe)
        }
    }

    fun scrollToNext(month: YearMonth) = scrollTo(month.next)

    fun scrollToPrev(month: YearMonth) = scrollTo(month.previous)

    fun notifyDayChanged(day: CalendarDay) = calendarAdapter.reloadDay(day)

    fun notifyMonthChanged(day: CalendarDay) = notifyMonthChanged(day.getMonth())

    fun notifyMonthChanged(month: YearMonth) {
        if (calendarType == CalendarType.GREGORIAN) {
            calendarAdapter.reloadMonth(month)
        } else {
            render().also { scrollTo(month) }
        }
    }

    fun getMonthName(month: CalendarMonth) = month.month.monthName(isHijri, Locale(calendarLocale))

    // endregion

    companion object {
        private const val SQUARE = Int.MIN_VALUE
        val SIZE_SQUARE = Size(SQUARE, SQUARE)
    }
}