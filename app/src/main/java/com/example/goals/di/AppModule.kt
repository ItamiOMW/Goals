package com.example.goals.di

import android.app.Application
import androidx.room.Room
import com.example.goals.data.local.GoalsDao
import com.example.goals.data.local.GoalsDatabase
import com.example.goals.data.local.TasksDao
import com.example.goals.data.repository.GoalsRepositoryImpl
import com.example.goals.data.repository.TasksRepositoryImpl
import com.example.goals.domain.repository.GoalsRepository
import com.example.goals.domain.repository.TasksRepository
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
        fun provideGoalsDatabase(application: Application): GoalsDatabase {
            return Room.databaseBuilder(
                application,
                GoalsDatabase::class.java,
                GoalsDatabase.DB_NAME
            ).build()
        }

    }
}