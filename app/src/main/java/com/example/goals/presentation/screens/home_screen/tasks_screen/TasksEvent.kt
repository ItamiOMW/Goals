package com.example.goals.presentation.screens.home_screen.tasks_screen

import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task

sealed class TasksEvent {

    data class GetTasksByDate(val date: String): TasksEvent()

    data class OnCompleteTask(val task: Task) : TasksEvent()

    data class OnCompleteSubTask(val task: Task, val subTask: SubTask): TasksEvent()

}
