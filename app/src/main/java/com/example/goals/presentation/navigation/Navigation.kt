package com.example.goals.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.goals.presentation.screens.add_edit_goal_screen.AddEditGoalScreen
import com.example.goals.presentation.screens.add_edit_note_screen.AddEditNoteScreen
import com.example.goals.presentation.screens.add_edit_task_screen.AddEditTaskScreen
import com.example.goals.presentation.screens.goals_screen.GoalsScreen
import com.example.goals.presentation.screens.home_screen.HomeScreen
import com.example.goals.presentation.screens.notes_screen.NotesScreen
import com.example.goals.presentation.screens.home_screen.tasks_screen.TasksScreen


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
            GoalsScreen()
        }
        composable(route = Destination.NotesScreen.route) {
            NotesScreen()
        }
        composable(route = Destination.AddEditGoalScreen.route) {
            AddEditGoalScreen()
        }
        composable(route = Destination.AddEditTaskScreen.route) {
            AddEditTaskScreen()
        }
        composable(route = Destination.AddEditNoteScreen.route) {
            AddEditNoteScreen()
        }
    }
}