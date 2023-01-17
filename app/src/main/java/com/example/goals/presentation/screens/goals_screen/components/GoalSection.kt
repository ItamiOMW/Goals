package com.example.goals.presentation.screens.goals_screen.components


sealed class GoalSection() {

    object AchievedGoalsSection: GoalSection()

    object NotAchievedTasksSection: GoalSection()

}
