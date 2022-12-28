package com.example.goals.presentation.screens.home_screen

import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task

sealed class HomeEvent {

    data class OnCompleteTask(val task: Task) : HomeEvent()

    data class OnCompleteSubTask(val task: Task, val subTask: SubTask): HomeEvent()

}
