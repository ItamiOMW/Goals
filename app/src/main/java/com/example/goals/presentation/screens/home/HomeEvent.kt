package com.example.goals.presentation.screens.home

import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task
import com.example.goals.domain.utils.order.TaskOrder

sealed class HomeEvent {

    data class OnCompleteTask(val task: Task) : HomeEvent()

    data class OnCompleteSubTask(val task: Task, val subTask: SubTask): HomeEvent()

    data class OrderChange(val taskOrder: TaskOrder): HomeEvent()

    object ToggleOrderSection: HomeEvent()

}
