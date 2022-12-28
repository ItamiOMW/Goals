package com.example.goals.presentation.screens.home_screen

import com.example.goals.domain.models.Task
import com.example.goals.utils.getCurrentDateString

data class HomeState(
    val todaysUncompletedTasks: List<Task> = emptyList(),
    val date: String = getCurrentDateString()
)

