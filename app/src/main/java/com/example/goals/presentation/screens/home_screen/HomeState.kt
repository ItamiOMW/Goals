package com.example.goals.presentation.screens.home_screen

import com.example.goals.domain.models.Task
import com.example.goals.domain.utils.order.OrderType
import com.example.goals.domain.utils.order.TaskOrder
import com.example.goals.presentation.screens.home_screen.components.TaskSection
import com.example.goals.utils.getCurrentDateString
import com.example.goals.utils.getCurrentDateStringFormatted

data class HomeState(
    val todaysUncompletedTasks: List<Task> = emptyList(),
    val todaysCompletedTasks: List<Task> = emptyList(),
    val date: String = getCurrentDateString(),
    val formattedDate: String = getCurrentDateStringFormatted(),
    val taskOrder: TaskOrder = TaskOrder.Time(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false,
    val taskSection: TaskSection = TaskSection.UncompletedTasksSection
)

