package com.example.goals.presentation.components

import android.widget.CalendarView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.goals.R
import com.example.goals.utils.formatDateToMillis
import com.example.goals.utils.formatDateToString


@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    date: String,
    onDateChanged: (String) -> Unit,
) {

    val context = LocalContext.current

    AndroidView(
        {
            CalendarView(
                ContextThemeWrapper(context, R.style.CalendarViewCustom)
            )
        },
        modifier = modifier,
        update = { mCalendar ->
            mCalendar.setOnDateChangeListener { mView, year, month, dayOfMonth ->
                val mDate = formatDateToString(dayOfMonth, month + 1, year)
                onDateChanged.invoke(mDate)
            }
            mCalendar.date = date.formatDateToMillis()
        }
    )

}