package com.example.goals.domain.repository

import com.example.goals.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getTasks(): Flow<List<Task>>

    suspend fun getTaskById(id: Int): Task?

    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)

}