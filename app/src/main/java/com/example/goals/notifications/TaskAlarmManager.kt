package com.example.goals.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import com.example.goals.domain.models.Task
import com.example.goals.utils.combineDateAndTime
import java.time.ZoneId
import javax.inject.Inject

class TaskAlarmManager @Inject constructor(
    private val application: Application,
    private val alarmManager: AlarmManager,
) : TaskAlarmScheduler {


    @SuppressLint("MissingPermission")
    override fun schedule(task: Task) {
        val intent = Intent(application, TaskAlarmReceiver::class.java).apply {
            putExtra(TaskAlarmReceiver.EXTRA_MESSAGE_KEY, task.title)
        }
        val localDateTime = combineDateAndTime(task.scheduledDate, task.scheduledTimeStart)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * MILLIS_IN_SECOND,
            PendingIntent.getBroadcast(
                application,
                task.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(task: Task) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                application,
                task.id,
                Intent(application, TaskAlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    companion object {

        const val MILLIS_IN_SECOND = 1000
    }
}