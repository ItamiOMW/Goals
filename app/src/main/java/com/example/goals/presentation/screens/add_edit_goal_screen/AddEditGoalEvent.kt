package com.example.goals.presentation.screens.add_edit_goal_screen

sealed class AddEditGoalEvent {

    data class BottomSheetTextChanged(val text: String): AddEditGoalEvent()

    data class SubGoalItemSelected(val index: Int): AddEditGoalEvent()

    data class DeadlineChange(val deadline: String) : AddEditGoalEvent()

    data class ColorChange(val colorInt: Int) : AddEditGoalEvent()

    data class TitleTextChange(val text: String) : AddEditGoalEvent()

    data class ContentTextChange(val text: String) : AddEditGoalEvent()

    object SaveGoal : AddEditGoalEvent()

    object SaveSubGoal: AddEditGoalEvent()

    object DeleteSubGoal : AddEditGoalEvent()

    data class ChangeSubGoalCompleteness(val indexSubGoal: Int) : AddEditGoalEvent()

}