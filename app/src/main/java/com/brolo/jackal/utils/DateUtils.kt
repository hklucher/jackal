package com.brolo.jackal.utils

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormatterBuilder

class DateUtils {
    companion object {
        fun formatDate(date: LocalDate): String {
            val formatter = DateTimeFormatterBuilder()
                .appendMonthOfYearText()
                .appendLiteral(" ")
                .appendDayOfMonth(1)
                .appendLiteral(" ")
                .appendYear(4, 4)
                .toFormatter()

            return date.toString(formatter)
        }
    }
}
