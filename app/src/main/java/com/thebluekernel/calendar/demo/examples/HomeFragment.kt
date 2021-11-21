package com.thebluekernel.calendar.demo.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.thebluekernel.calendar.demo.databinding.FragmentHomeBinding
import com.thebluekernel.calendar.demo.utils.DayViewContainer
import com.thebluekernel.calendar.library.data.model.CalendarDay
import com.thebluekernel.calendar.library.data.model.CalendarMonth
import com.thebluekernel.calendar.library.data.model.monthName
import com.thebluekernel.calendar.library.data.model.yearValue
import com.thebluekernel.calendar.library.data.ui.CalendarDayBinder
import com.thebluekernel.calendar.library.data.ui.CalendarMonthBinder

/**
 * Created by Ahmed Ibrahim on 21,November,2021
 */
private typealias directions = HomeFragmentDirections

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val navigator by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() = with(binding) {
        tvBasicExample.setOnClickListener { navigateTo(directions.actionHomeFragmentToBasicCalendarExampleFragment()) }
        tvEventsExample.setOnClickListener { navigateTo(directions.actionHomeFragmentToEventCalendarExampleFragment()) }
    }

    private fun navigateTo(directions: NavDirections) {
        navigator.navigate(directions)
    }

}