package com.example.goals.presentation.components

import androidx.compose.runtime.Composable
import com.example.goals.presentation.ui.theme.Blue
import com.example.goals.presentation.ui.theme.GrayShadeLight
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.model.KalendarType
import kotlinx.datetime.toJavaLocalDate


@Composable
fun Calendar(
    onDateChange: (java.time.LocalDate) -> Unit,
) {
    Kalendar(
        kalendarType = KalendarType.Oceanic(true),
        kalendarThemeColor = KalendarThemeColor(GrayShadeLight, Blue, Blue),
        onCurrentDayClick = { day, _ ->
            onDateChange(day.localDate.toJavaLocalDate())
        }
    )
}