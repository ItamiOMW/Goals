package com.example.goals.presentation.components

import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.goals.utils.formatDateToString
import java.util.*


@Composable
fun DatePickerDialog(
    showDialog: Boolean,
    onCancelled: () -> Unit,
    onDatePicked: (date: String) -> Unit,
) {

    val calendar = Calendar.getInstance()

    val year by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val month by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val day by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    val context = LocalContext.current

    val datePicker = android.app.DatePickerDialog(context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onDatePicked.invoke(formatDateToString(mDayOfMonth, mMonth + 1, mYear))
        },
        year,
        month,
        day)

    datePicker.setOnCancelListener {
        onCancelled.invoke()
    }

    if (showDialog) {
        datePicker.show()
    }
}