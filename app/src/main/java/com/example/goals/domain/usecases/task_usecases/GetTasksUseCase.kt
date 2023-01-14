package com.example.goals.domain.usecases.task_usecases

import com.example.goals.domain.repository.TasksRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TasksRepository,
) {

    operator fun invoke() = repository.getTasks()

}