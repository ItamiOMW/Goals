package com.example.goals.notifications

import android.app.*
import android.content.Intent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import com.example.goals.R
import com.example.goals.presentation.screens.MainActivity
import javax.inject.Inject

class TaskNotificationsService @Inject constructor (
    private val application: Application,
    private val notificationManager: NotificationManager
): NotificationService {

    companion object {

        const val CHANNEL_ID = "Task Channel"

        const val NOTIFICATION_ID = 1

        const val ACTIVITY_REQUEST_CODE = 321
    }



    @OptIn(ExperimentalMaterialApi::class)
    override fun showNotification(message: String) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            application.getString(R.string.reminder),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
        val activityIntent = Intent(application, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            application,
            ACTIVITY_REQUEST_CODE,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(application, CHANNEL_ID)
            .setSmallIcon(R.drawable.splash_icon)
            .setContentTitle(application.getString(R.string.reminder))
            .setContentText(message)
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(
            NOTIFICATION_ID,
            notification
        )

    }

}