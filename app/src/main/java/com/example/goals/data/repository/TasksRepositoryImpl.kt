package com.example.goals.data.repository

import com.example.goals.data.local.TasksDao
import com.example.goals.domain.models.Task
import com.example.goals.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor (
    private val dao: TasksDao,
) : TasksRepository {

    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }

    override suspend fun addTask(task: Task) {
        dao.addTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.id)
    }

}