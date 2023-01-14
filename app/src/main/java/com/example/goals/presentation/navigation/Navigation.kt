package com.example.goals.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.goals.presentation.navigation.Destination.*
import com.example.goals.presentation.navigation.Destination.Companion.GOAL_ID_ARG
import com.example.goals.presentation.navigation.Destination.Companion.NOTE_ID_ARG
import com.example.goals.presentation.navigation.Destination.Companion.TASK_ID_ARG
import com.example.goals.presentation.screens.add_edit_goal_screen.AddEditGoalScreen
import com.example.goals.presentation.screens.add_edit_note_screen.AddEditNoteScreen
import com.example.goals.presentation.screens.add_edit_task_screen.AddEditTaskScreen
import com.example.goals.presentation.screens.goal_info_screen.GoalInfoScreen
import com.example.goals.presentation.screens.goals_screen.GoalsScreen
import com.example.goals.presentation.screens.home_screen.HomeScreen
import com.example.goals.presentation.screens.note_info_screen.NoteInfoScreen
import com.example.goals.presentation.screens.notes_screen.NotesScreen
import com.example.goals.presentation.screens.tasks_screen.TasksScreen
import com.example.goals.utils.UNKNOWN_ID


@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = HomeScreen.route) {
        composable(route = HomeScreen.route) {
            HomeScreen()
        }
        composable(route = TasksScreen.route) {
            TasksScreen()
        }
        composable(route = GoalsScreen.route) {
            GoalsScreen(navController = navHostController)
        }
        composable(route = NotesScreen.route) {
            NotesScreen(navController = navHostController)
        }
        composable(
            route = AddEditGoalScreen.route + "?$GOAL_ID_ARG={$GOAL_ID_ARG}",
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
            route = AddEditTaskScreen.route + "?$TASK_ID_ARG={$TASK_ID_ARG}",
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
            route = AddEditNoteScreen.route + "?$NOTE_ID_ARG={$NOTE_ID_ARG}",
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
            route = GoalInfoScreen.route + "?${GOAL_ID_ARG}={$GOAL_ID_ARG}",
            arguments = listOf(
                navArgument(GOAL_ID_ARG) {
                    type = NavType.IntType
                    nullable
                }
            )
        ) {
            GoalInfoScreen(navController = navHostController)
        }
        composable(
            route = NoteInfoScreen.route + "?${NOTE_ID_ARG}={$NOTE_ID_ARG}",
            arguments = listOf(
                navArgument(NOTE_ID_ARG) {
                    type = NavType.IntType
                    nullable
                }
            )
        ) { entry ->
            NoteInfoScreen(navController = navHostController)
        }
    }
}