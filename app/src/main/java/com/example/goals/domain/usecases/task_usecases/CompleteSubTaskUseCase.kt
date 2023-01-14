package com.example.goals.domain.usecases.task_usecases

import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task
import com.example.goals.domain.repository.TasksRepository
import javax.inject.Inject

class CompleteSubTaskUseCase @Inject constructor(
    private val repository: TasksRepository,
) {

    suspend operator fun invoke(subTask: SubTask, task: Task) =
        repository.completeSubTask(subTask, task)

}