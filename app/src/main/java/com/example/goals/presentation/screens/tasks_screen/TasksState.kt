package com.example.goals.presentation.screens.tasks_screen

import com.example.goals.domain.models.Task
import com.example.goals.utils.getCurrentDateString

data class TasksState(
    val date: String = getCurrentDateString(),
    val listTasksByDate: List<Task> = emptyList(),
    val message: String = "",
)