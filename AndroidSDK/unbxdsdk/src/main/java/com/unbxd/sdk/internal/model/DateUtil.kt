package com.unbxd.sdk.internal.model

import java.time.LocalDate
import java.util.*

class DateUtil {
    companion object {
        fun getDateByAddingYears(years: Int): Date {
            var calendar = Calendar.getInstance()
            //calendar.time = Date()
            calendar.add(Calendar.YEAR, years)
            return calendar.time
        }

        fun getDateByAddingMinutes(mins: Int): Date {
            var calendar = Calendar.getInstance()
            //calendar.time = Date()
            calendar.add(Calendar.MINUTE, mins)
            return calendar.time
        }
    }
}