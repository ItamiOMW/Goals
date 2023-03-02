package com.example.goals.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.goals.navigation.Screen.*
import com.example.goals.navigation.Screen.Companion.GOAL_ID_ARG
import com.example.goals.navigation.Screen.Companion.NOTE_ID_ARG
import com.example.goals.navigation.Screen.Companion.TASK_ID_ARG
import com.example.goals.presentation.screens.add_edit_goal.AddEditGoalScreen
import com.example.goals.presentation.screens.add_edit_note.AddEditNoteScreen
import com.example.goals.presentation.screens.add_edit_task.AddEditTaskScreen
import com.example.goals.presentation.screens.goal_info.GoalInfoScreen
import com.example.goals.presentation.screens.goals.GoalsScreen
import com.example.goals.presentation.screens.home.HomeScreen
import com.example.goals.presentation.screens.note_info.NoteInfoScreen
import com.example.goals.presentation.screens.notes.NotesScreen
import com.example.goals.presentation.screens.task_info.TaskInfoScreen
import com.example.goals.presentation.screens.tasks.TasksScreen
import com.example.goals.utils.UNKNOWN_ID


@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = HomeScreen.fullRoute) {
        composable(route = HomeScreen.fullRoute) {
            HomeScreen(navController = navHostController)
        }
        composable(route = TasksScreen.fullRoute) {
            TasksScreen(navController = navHostController)
        }
        composable(route = GoalsScreen.fullRoute) {
            GoalsScreen(navController = navHostController)
        }
        composable(route = NotesScreen.fullRoute) {
            NotesScreen(navController = navHostController)
        }
        composable(
            route = AddEditGoalScreen.fullRoute,
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
        composable(
            route = AddEditTaskScreen.fullRoute,
            arguments = listOf(
                navArgument(TASK_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = UNKNOWN_ID
                }
            )
        ) { entry ->
            val taskId = entry.arguments?.getInt(TASK_ID_ARG) ?: UNKNOWN_ID
            AddEditTaskScreen(navController = navHostController, taskId = taskId)
        }
        composable(
            route = AddEditNoteScreen.fullRoute,
            arguments = listOf(
                navArgument(NOTE_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = UNKNOWN_ID
                }
            )
        ) { entry ->
            val noteId = entry.arguments?.getInt(NOTE_ID_ARG) ?: UNKNOWN_ID
            AddEditNoteScreen(noteId = noteId, navController = navHostController)
        }
        composable(
            route = GoalInfoScreen.fullRoute,
            arguments = listOf(
                navArgument(GOAL_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = UNKNOWN_ID
                }
            )
        ) {
            GoalInfoScreen(navController = navHostController)
        }
        composable(
            route = NoteInfoScreen.fullRoute,
            arguments = listOf(
                navArgument(NOTE_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = UNKNOWN_ID
                }
            )
        ) {
            NoteInfoScreen(navController = navHostController)
        }
        composable(
            route = TaskInfoScreen.fullRoute,
            arguments = listOf(
                navArgument(TASK_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = UNKNOWN_ID
                }
            )
        ) {
            TaskInfoScreen(navController = navHostController)
        }
    }
}