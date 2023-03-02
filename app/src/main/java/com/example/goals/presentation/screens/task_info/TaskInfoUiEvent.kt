package com.example.goals.presentation.screens.task_info


sealed class TaskInfoUiEvent {

    object TaskDeleted: TaskInfoUiEvent()

    data class ShowToast(val message: String): TaskInfoUiEvent()
}