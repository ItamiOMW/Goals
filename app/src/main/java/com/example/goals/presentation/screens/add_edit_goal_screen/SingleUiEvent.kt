package com.example.goals.presentation.screens.add_edit_goal_screen

sealed class SingleUiEvent() {

    data class ShowToast(val message: String) : SingleUiEvent()

    object NavigateBack : SingleUiEvent()

}