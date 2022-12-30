package com.example.goals.presentation.screens.goals_screen

import com.example.goals.domain.models.Goal

data class GoalsState(
    val goals: List<Goal> = emptyList()
)