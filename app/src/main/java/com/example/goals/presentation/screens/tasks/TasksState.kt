package com.example.goals.presentation.screens.tasks

import com.example.goals.domain.models.Task
import com.example.goals.utils.getCurrentDateString

data class TasksState(
    val date: String = getCurrentDateString(),
    val listTasksByDate: List<Task> = emptyList(),
)