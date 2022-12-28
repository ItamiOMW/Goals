package com.example.goals.domain.repository

import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getTasks(): Flow<List<Task>>

    fun getTasksByDate(date: Long): Flow<List<Task>>

    suspend fun getTaskById(id: Int): Task?

    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun completeTask(task: Task)

    suspend fun completeSubTask(subTask: SubTask, task: Task)

    suspend fun editTask(task: Task)

}