package com.example.goals.domain.usecases.task_usecases

import com.example.goals.domain.repository.TasksRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val repository: TasksRepository,
) {

    operator fun invoke(id: Int) = repository.getTaskById(id)

}