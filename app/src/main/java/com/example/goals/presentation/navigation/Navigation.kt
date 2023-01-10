package com.example.goals.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.goals.presentation.navigation.Destination.AddEditGoalScreen.GOAL_ID_ARG
import com.example.goals.presentation.screens.add_edit_goal_screen.AddEditGoalScreen
import com.example.goals.presentation.screens.add_edit_note_screen.AddEditNoteScreen
import com.example.goals.presentation.screens.add_edit_task_screen.AddEditTaskScreen
import com.example.goals.presentation.screens.goal_info_screen.GoalInfoScreen
import com.example.goals.presentation.screens.goals_screen.GoalsScreen
import com.example.goals.presentation.screens.home_screen.HomeScreen
import com.example.goals.presentation.screens.notes_screen.NotesScreen
import com.example.goals.presentation.screens.tasks_screen.TasksScreen
import com.example.goals.utils.UNKNOWN_ID


@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Destination.HomeScreen.route) {
        composable(route = Destination.HomeScreen.route) {
            HomeScreen()
        }
        composable(route = Destination.TasksScreen.route) {
            TasksScreen()
        }
        composable(route = Destination.GoalsScreen.route) {
            GoalsScreen(navController = navHostController)
        }
        composable(route = Destination.NotesScreen.route) {
            NotesScreen()
        }
        composable(
            route = Destination.AddEditGoalScreen.route + "?$GOAL_ID_ARG={$GOAL_ID_ARG}",
            arguments = listOf(
                navArgument(GOAL_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = UNKNOWN_ID
                }
            )
        ) { entry ->
            val goalId = entry.arguments?.getInt(GOAL_ID_ARG) ?: UNKNOWN_ID
            AddEditGoalScreen(navController = navHostController, goalId = goalId)
        }
        composable(route = Destination.AddEditTaskScreen.route) {
            AddEditTaskScreen()
        }
        composable(route = Destination.AddEditNoteScreen.route) {
            AddEditNoteScreen()
        }
        composable(
            route = Destination.GoalInfoScreen.route + "?${Destination.GoalInfoScreen.GOAL_ID_ARG}={$GOAL_ID_ARG}",
            arguments = listOf(
                navArgument(GOAL_ID_ARG) {
                    type = NavType.IntType
                    nullable
                }
            )
        ) {
            GoalInfoScreen(navController = navHostController)
        }
    }
}