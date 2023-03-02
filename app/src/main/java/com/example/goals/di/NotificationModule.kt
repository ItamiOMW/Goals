package com.example.goals.di

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import com.example.goals.notifications.NotificationService
import com.example.goals.notifications.TaskAlarmManager
import com.example.goals.notifications.TaskAlarmScheduler
import com.example.goals.notifications.TaskNotificationsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideTaskAlarmManager(
        taskAlarmManager: TaskAlarmManager,
    ): TaskAlarmScheduler = taskAlarmManager

    @Provides
    @Singleton
    fun provideTaskNotificationService(
        taskNotificationsService: TaskNotificationsService,
    ): NotificationService = taskNotificationsService

    @Provides
    @Singleton
    fun provideAlarmManager(
        application: Application,
    ): AlarmManager {
        return application.getSystemService(AlarmManager::class.java)
    }


    @Provides
    @Singleton
    fun provideNotificationManager(
        application: Application,
    ): NotificationManager {
        return application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}