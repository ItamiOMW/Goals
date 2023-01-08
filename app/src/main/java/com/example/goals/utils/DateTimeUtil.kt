package com.example.goals.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


fun getCurrentDateString(): String {
    return LocalDate.now().toString()
}

fun getDateDaysInAdvance(daysToAdd: Long): String {
    return LocalDate.now().plusDays(daysToAdd).toString()
}

fun String.formatDate(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(this, dateFormatter)
    val month = date.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
    val year = date.year.toString()
    val dayOfMonth = date.dayOfMonth.toString()
    return "$dayOfMonth $month, $year"
}

fun formatDateToString(dayOfMonth: Int, month: Int, year: Int): String {
    return LocalDate.of(year, month, dayOfMonth).toString()
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

fun Long.toTimeString(): String {
    val dateTime = Date(this)
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(dateTime)
}

fun String.toTimeLong(): Long {
    val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return format.parse(this)?.time ?: throw IllegalArgumentException("Invalid time string")
}

fun Long.toDateString(): String {
    val localDate = LocalDate.ofEpochDay(this)
    val date = Date(localDate.toEpochDay())
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
}

fun Long.toTimeDateString(): String {
    val dateTime = Date(this)
    val format = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.US)
    return format.format(dateTime)
}

fun String.toTimeDateLong(): Long {
    val format = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.US)
    return format.parse(this)?.time ?: throw IllegalArgumentException("Invalid time string")
}
