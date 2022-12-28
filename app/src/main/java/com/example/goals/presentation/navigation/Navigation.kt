package com.example.goals.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.goals.presentation.screens.goals_screen.GoalsScreen
import com.example.goals.presentation.screens.home_screen.HomeScreen
import com.example.goals.presentation.screens.tasks_screen.TasksScreen


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
    }
}