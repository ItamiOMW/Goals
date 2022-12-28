package com.example.goals.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun getCurrentDateLong(): Long {
    return LocalDate.now().toEpochDay()
}

fun getCurrentDateString(): String {
    val localDate = System.currentTimeMillis()
    val date = Date(localDate)
    return SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(date)
}

fun Long.toTimeString(): String {
    val dateTime = Date(this)
    val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return format.format(dateTime)
}

fun String.toTimeLong(): Long {
    val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return format.parse(this)?.time ?: throw IllegalArgumentException("Invalid time string")
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

