package com.example.goals.di

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import com.example.goals.data.local.GoalsDatabase
import com.example.goals.data.local.dao.GoalsDao
import com.example.goals.data.local.dao.NotesDao
import com.example.goals.data.local.dao.TasksDao
import com.example.goals.data.repository.GoalsRepositoryImpl
import com.example.goals.data.repository.NotesRepositoryImpl
import com.example.goals.data.repository.TasksRepositoryImpl
import com.example.goals.domain.repository.GoalsRepository
import com.example.goals.domain.repository.NotesRepository
import com.example.goals.domain.repository.TasksRepository
import com.example.goals.notifications.NotificationService
import com.example.goals.notifications.TaskAlarmManager
import com.example.goals.notifications.TaskAlarmScheduler
import com.example.goals.notifications.TaskNotificationsService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindGoalsRepository(goalsRepositoryImpl: GoalsRepositoryImpl): GoalsRepository

    @Binds
    @Singleton
    fun bindTasksRepository(tasksRepositoryImpl: TasksRepositoryImpl): TasksRepository

    @Binds
    @Singleton
    fun bindNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository

    @Binds
    @Singleton
    fun provideTaskAlarmManager(taskAlarmManager: TaskAlarmManager): TaskAlarmScheduler

    @Binds
    @Singleton
    fun provideTaskNotificationService(taskNotificationsService: TaskNotificationsService): NotificationService

    companion object {

        @Provides
        @Singleton
        fun provideGoalsDao(db: GoalsDatabase): GoalsDao {
            return db.goalsDao()
        }

        @Provides
        @Singleton
        fun provideTasksDao(db: GoalsDatabase): TasksDao {
            return db.tasksDao()
        }

        @Provides
        @Singleton
        fun provideNotesDao(db: GoalsDatabase): NotesDao {
            return db.notesDao()
        }

        @Provides
        @Singleton
        fun provideGoalsDatabase(application: Application): GoalsDatabase {
            return Room.databaseBuilder(
                application,
                GoalsDatabase::class.java,
                GoalsDatabase.DB_NAME
            ).build()
        }

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
}