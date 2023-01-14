package com.example.goals.domain.repository

import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getTasks(): Flow<List<Task>>

    fun getTasksByDate(date: String): Flow<List<Task>>

    fun getUncompletedTasksByDate(date: String): Flow<List<Task>>

    fun getTaskById(id: Int): Flow<Task?>

    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun completeTask(task: Task)

    suspend fun completeSubTask(subTask: SubTask, task: Task)

    suspend fun editTask(task: Task)

}