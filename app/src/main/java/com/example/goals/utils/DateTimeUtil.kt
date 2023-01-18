package com.example.goals.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


const val DATE_FORMAT = "yyyy-MM-dd"

const val TIME_FORMAT = "HH:mm"

fun Long.timeSecondsToString(): String {
    val timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT)
    return LocalTime.ofSecondOfDay(this).format(timeFormatter)
}

fun getCurrentDateString(): String {
    return LocalDate.now().toString()
}

fun getCurrentTimeSeconds(): Long {
    return LocalTime.now().toSecondOfDay().toLong()
}

fun getDateDaysInAdvance(daysToAdd: Long): String {
    return LocalDate.now().plusDays(daysToAdd).toString()
}

fun String.formatDate(): String {
    val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    val date = LocalDate.parse(this, dateFormatter)
    val month = date.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
    val year = date.year.toString()
    val dayOfMonth = date.dayOfMonth.toString()
    return "$dayOfMonth $month, $year"
}

fun String.formatDateToLong(): Long {
    val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    val date = LocalDate.parse(this, dateFormatter)
    return "${date.year}${date.monthValue}${date.dayOfMonth}".toLong()
}

fun formatDateToString(dayOfMonth: Int, month: Int, year: Int): String {
    return LocalDate.of(year, month, dayOfMonth).toString()
}

fun formatTimeToLong(hour: Int, minute: Int): Long {
    return LocalTime.of(hour, minute).toSecondOfDay().toLong()
}

fun String.stringDateToFormatted(): String {
    val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    val localDateLong = LocalDate.parse(this, dateFormatter)
    val dayOfWeek =
        localDateLong.dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val dayOfMonth = localDateLong.dayOfMonth.toString()
    val month = localDateLong.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    return "$dayOfWeek, $dayOfMonth $month"
}

fun getCurrentDateStringFormatted(): String {
    val localDateLong = LocalDate.now()
    val dayOfWeek =
        localDateLong.dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val dayOfMonth = localDateLong.dayOfMonth.toString()
    val month = localDateLong.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    return "$dayOfWeek, $dayOfMonth $month"
}


fun combineDateAndTime(date: String, time: Long): LocalDateTime {
    val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    val localDate = LocalDate.parse(date, dateFormatter)
    val localTime = LocalTime.ofSecondOfDay(time)
    return LocalDateTime.of(localDate, localTime)
}

