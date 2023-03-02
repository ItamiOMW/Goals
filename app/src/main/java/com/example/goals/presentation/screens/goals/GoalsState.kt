package com.example.goals.presentation.screens.goals

import com.example.goals.domain.models.Goal
import com.example.goals.domain.utils.order.GoalOrder
import com.example.goals.domain.utils.order.OrderType

data class GoalsState(
    val achievedGoals: List<Goal> = emptyList(),
    val notAchievedGoals: List<Goal> = emptyList(),
    val isOrderSectionVisible: Boolean = false,
    val goalOrder: GoalOrder = GoalOrder.Date(OrderType.Ascending),
)