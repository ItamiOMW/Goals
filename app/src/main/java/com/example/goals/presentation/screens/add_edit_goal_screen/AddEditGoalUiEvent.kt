package com.example.goals.presentation.screens.add_edit_goal_screen

sealed class AddEditGoalUiEvent() {

    data class ShowToast(val message: String) : AddEditGoalUiEvent()

    object GoalSaved : AddEditGoalUiEvent()

}