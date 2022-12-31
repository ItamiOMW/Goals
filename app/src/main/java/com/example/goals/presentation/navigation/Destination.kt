package com.example.goals.presentation.navigation

sealed class Destination(val route: String) {

    object HomeScreen : Destination(HOME_SCREEN_ROUTE)

    object TasksScreen : Destination(TASKS_SCREEN_ROUTE)

    object GoalsScreen : Destination(GOALS_SCREEN_ROUTE)

    object NotesScreen : Destination(NOTES_SCREEN_ROUTE)

    object AddEditGoalScreen : Destination(ADD_EDIT_GOAL_SCREEN_ROUTE)

    object AddEditTaskScreen : Destination(ADD_EDIT_TASK_SCREEN_ROUTE)

    object AddEditNoteScreen : Destination(ADD_EDIT_NOTE_SCREEN_ROUTE)

    companion object {

        const val HOME_SCREEN_ROUTE = "home"

        const val TASKS_SCREEN_ROUTE = "tasks"

        const val GOALS_SCREEN_ROUTE = "goals"

        const val NOTES_SCREEN_ROUTE = "notes"

        const val ADD_EDIT_GOAL_SCREEN_ROUTE = "add_edit_goal"

        const val ADD_EDIT_TASK_SCREEN_ROUTE = "add_edit_task"

        const val ADD_EDIT_NOTE_SCREEN_ROUTE = "add_edit_note"

    }
}