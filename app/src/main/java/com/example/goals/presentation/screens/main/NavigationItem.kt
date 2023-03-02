package com.example.goals.presentation.screens.main

import com.example.goals.R
import com.example.goals.navigation.Screen

sealed class NavigationItem(
    val nameResId: Int,
    val screen: Screen,
    val iconResId: Int,
    val badgeCount: Int = 0,
) {

    data class Home(val badges: Int) : NavigationItem(
        nameResId = R.string.home,
        screen = Screen.HomeScreen,
        iconResId = R.drawable.home,
        badgeCount = badges
    )

    object Tasks : NavigationItem(
        nameResId = R.string.tasks,
        screen = Screen.TasksScreen,
        iconResId = R.drawable.list_check,
    )

    object Goals : NavigationItem(
        nameResId = R.string.goals_title,
        screen = Screen.GoalsScreen,
        iconResId = R.drawable.goal,
    )

    object Notes : NavigationItem(
        nameResId = R.string.notes,
        screen = Screen.NotesScreen,
        iconResId = R.drawable.notes,
    )

    object AddTask : NavigationItem(
        nameResId = R.string.add_task,
        screen = Screen.AddEditTaskScreen,
        iconResId = R.drawable.task
    )

    object AddGoal : NavigationItem(
        nameResId = R.string.add_goal,
        screen = Screen.AddEditGoalScreen,
        iconResId = R.drawable.goal
    )

    object AddNote : NavigationItem(
        nameResId = R.string.add_note,
        screen = Screen.AddEditNoteScreen,
        iconResId = R.drawable.edit
    )

}