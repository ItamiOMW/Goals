package com.example.goals.presentation.screens.goal_info_screen

sealed class GoalInfoUiEvent {


    object GoalDeleted: GoalInfoUiEvent()

    data class ShowToast(val message: String): GoalInfoUiEvent()
}