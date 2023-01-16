package com.example.goals.presentation.screens.task_info_screen

import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task

sealed class TaskInfoEvent {

    object CompleteTask : TaskInfoEvent()

    object DeleteTask : TaskInfoEvent()

    data class ChangeSubTaskCompleteness(val subTask: SubTask, val task: Task) : TaskInfoEvent()
}