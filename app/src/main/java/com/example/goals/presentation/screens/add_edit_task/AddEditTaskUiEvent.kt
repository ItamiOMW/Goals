package com.example.goals.presentation.screens.add_edit_task

sealed class AddEditTaskUiEvent {

    object TaskSaved: AddEditTaskUiEvent()

    data class ShowToast(val message: String): AddEditTaskUiEvent()

}