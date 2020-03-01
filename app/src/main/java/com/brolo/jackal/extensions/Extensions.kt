package com.brolo.jackal.extensions

import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.LocalDate

fun Interval.toLocalDates(): Sequence<LocalDate> = generateSequence(start) { d ->
    d.plusDays(1).takeIf { it < end }
}.map(DateTime::toLocalDate)
