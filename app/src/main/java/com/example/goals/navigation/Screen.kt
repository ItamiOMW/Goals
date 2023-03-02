package com.example.goals.navigation

sealed class Screen(protected val route: String, vararg params: String) {

    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    object HomeScreen : Screen(
        route = HOME_SCREEN_ROUTE,
    )

    object TasksScreen : Screen(
        route = TASKS_SCREEN_ROUTE
    )

    object GoalsScreen : Screen(
        route = GOALS_SCREEN_ROUTE
    )

    object NotesScreen : Screen(
        route = NOTES_SCREEN_ROUTE
    )

    object AddEditGoalScreen : Screen(
        route = ADD_EDIT_GOAL_SCREEN_ROUTE,
        GOAL_ID_ARG
    ) {
        fun getRouteWithArgs(goalId: Int?): String = route.appendParams(
            GOAL_ID_ARG to goalId
        )

    }

    object AddEditTaskScreen : Screen(
        route = ADD_EDIT_TASK_SCREEN_ROUTE,
        TASK_ID_ARG
    ) {
        fun getRouteWithArgs(taskId: Int): String = route.appendParams(
            TASK_ID_ARG to taskId
        )
    }

    object AddEditNoteScreen : Screen(
        route = ADD_EDIT_NOTE_SCREEN_ROUTE,
        NOTE_ID_ARG
    ) {
        fun getRouteWithArgs(noteId: Int): String = route.appendParams(
            NOTE_ID_ARG to noteId
        )
    }

    object GoalInfoScreen : Screen(
        route = GOAL_INFO_SCREEN_ROUTE,
        GOAL_ID_ARG
    ) {
        fun getRouteWithArgs(goalId: Int): String = route.appendParams(
            GOAL_ID_ARG to goalId
        )
    }

    object NoteInfoScreen : Screen(
        route = NOTE_INFO_SCREEN_ROUTE,
        NOTE_ID_ARG
    ) {
        fun getRouteWithArgs(noteId: Int): String = route.appendParams(
            NOTE_ID_ARG to noteId
        )
    }

    object TaskInfoScreen : Screen(
        route = TASK_INFO_SCREEN_ROUTE,
        TASK_ID_ARG
    ) {
        fun getRouteWithArgs(taskId: Int): String = route.appendParams(
            TASK_ID_ARG to taskId
        )
    }

    companion object {

        private const val HOME_SCREEN_ROUTE = "home"

        private const val TASKS_SCREEN_ROUTE = "tasks"

        private const val GOALS_SCREEN_ROUTE = "goals"

        private const val NOTES_SCREEN_ROUTE = "notes"

        private const val ADD_EDIT_GOAL_SCREEN_ROUTE = "add_edit_goal"

        private const val ADD_EDIT_TASK_SCREEN_ROUTE = "add_edit_task"

        private const val ADD_EDIT_NOTE_SCREEN_ROUTE = "add_edit_note"

        private const val GOAL_INFO_SCREEN_ROUTE = "goal_info"

        private const val NOTE_INFO_SCREEN_ROUTE = "note_info"

        private const val TASK_INFO_SCREEN_ROUTE = "task_info"

        const val GOAL_ID_ARG = "goalId"

        const val NOTE_ID_ARG = "noteId"

        const val TASK_ID_ARG = "taskId"

    }

    internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
        val builder = StringBuilder(this)

        params.forEach {
            it.second?.toString()?.let { arg ->
                builder.append("/$arg")
            }
        }

        return builder.toString()
    }
}