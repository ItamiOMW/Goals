package com.example.goals.presentation.screens.task_info_screen


sealed class TaskInfoUiEvent {

    object TaskDeleted: TaskInfoUiEvent()

    data class ShowToast(val message: String): TaskInfoUiEvent()
}