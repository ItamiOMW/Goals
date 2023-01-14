package com.example.goals.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskAlarmReceiver @Inject constructor (
) : BroadcastReceiver() {

    @Inject
    lateinit var taskNotificationService: NotificationService

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(EXTRA_MESSAGE_KEY) ?: return
        taskNotificationService.showNotification(message)
    }

    companion object {

        const val EXTRA_MESSAGE_KEY = "EXTRA_MESSAGE"

    }

}