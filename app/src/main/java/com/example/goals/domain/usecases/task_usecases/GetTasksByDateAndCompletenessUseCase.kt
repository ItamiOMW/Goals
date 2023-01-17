package com.example.goals.domain.usecases.task_usecases

import com.example.goals.domain.models.Task
import com.example.goals.domain.repository.TasksRepository
import com.example.goals.domain.utils.order.OrderType
import com.example.goals.domain.utils.order.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTasksByDateAndCompletenessUseCase @Inject constructor(
    private val repository: TasksRepository
) {

    operator fun invoke(
        taskOrder: TaskOrder = TaskOrder.Time(OrderType.Ascending),
        isCompleted: Boolean,
        date: String,
    ): Flow<List<Task>> {
        return repository.getTasksByDateAndCompleteness(date, isCompleted).map { tasks ->
            when (taskOrder.orderType) {
                is OrderType.Ascending -> {
                    when (taskOrder) {
                        is TaskOrder.Time -> tasks.sortedBy { it.scheduledTimeStart }
                        is TaskOrder.Title -> tasks.sortedBy { it.title.lowercase() }
                    }
                }
                is OrderType.Descending -> {
                    when (taskOrder) {
                        is TaskOrder.Time -> tasks.sortedByDescending { it.scheduledTimeStart }
                        is TaskOrder.Title -> tasks.sortedByDescending { it.title.lowercase() }
                    }
                }
            }
        }
    }

}