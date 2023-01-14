package com.example.goals.domain.usecases.task_usecases

import com.example.goals.domain.models.Task
import com.example.goals.domain.repository.TasksRepository
import com.example.goals.notifications.TaskAlarmScheduler
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val repository: TasksRepository,
    private val taskAlarmScheduler: TaskAlarmScheduler,
) {

    suspend operator fun invoke(task: Task) {
        repository.completeTask(task)
        taskAlarmScheduler.cancel(task)
    }

}