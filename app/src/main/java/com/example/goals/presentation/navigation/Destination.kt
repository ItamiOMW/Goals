package com.example.goals.presentation.navigation

sealed class Destination(val route: String) {

    object HomeScreen : Destination(HOME_SCREEN_ROUTE)

    object TasksScreen : Destination(TASKS_SCREEN_ROUTE)

    object GoalsScreen : Destination(GOALS_SCREEN_ROUTE)

    companion object {

        const val HOME_SCREEN_ROUTE = "home"

        const val TASKS_SCREEN_ROUTE = "tasks"

        const val GOALS_SCREEN_ROUTE = "goals"

    }
}