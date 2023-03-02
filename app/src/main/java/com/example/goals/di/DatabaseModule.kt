package com.example.goals.di

import android.app.Application
import androidx.room.Room
import com.example.goals.data.local.GoalsDatabase
import com.example.goals.data.local.dao.GoalsDao
import com.example.goals.data.local.dao.NotesDao
import com.example.goals.data.local.dao.TasksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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

}