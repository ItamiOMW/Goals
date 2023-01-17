package com.example.goals.presentation.screens.home_screen.components

sealed class TaskSection() {

    object CompletedTasksSection: TaskSection()

    object UncompletedTasksSection: TaskSection()

}
