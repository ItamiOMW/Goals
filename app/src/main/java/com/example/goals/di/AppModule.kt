package com.example.goals.di

import com.example.goals.data.repository.GoalsRepositoryImpl
import com.example.goals.data.repository.NotesRepositoryImpl
import com.example.goals.data.repository.TasksRepositoryImpl
import com.example.goals.domain.repository.GoalsRepository
import com.example.goals.domain.repository.NotesRepository
import com.example.goals.domain.repository.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun bindGoalsRepository(
        goalsRepositoryImpl: GoalsRepositoryImpl,
    ): GoalsRepository = goalsRepositoryImpl

    @Provides
    @Singleton
    fun bindTasksRepository(
        tasksRepositoryImpl: TasksRepositoryImpl,
    ): TasksRepository = tasksRepositoryImpl

    @Provides
    @Singleton
    fun bindNotesRepository(
        notesRepositoryImpl: NotesRepositoryImpl,
    ): NotesRepository = notesRepositoryImpl

}