package com.example.goals.presentation.screens.goals_screen

import com.example.goals.domain.models.Goal
import com.example.goals.domain.utils.order.GoalOrder
import com.example.goals.domain.utils.order.OrderType
import com.example.goals.presentation.screens.goals_screen.components.GoalSection

data class GoalsState(
    val achievedGoals: List<Goal> = emptyList(),
    val notAchievedGoals: List<Goal> = emptyList(),
    val isOrderSectionVisible: Boolean = false,
    val goalOrder: GoalOrder = GoalOrder.Date(OrderType.Ascending),
    val goalSection: GoalSection = GoalSection.NotAchievedTasksSection
)