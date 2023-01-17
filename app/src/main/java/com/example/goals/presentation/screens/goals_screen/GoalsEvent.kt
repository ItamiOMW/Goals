package com.example.goals.presentation.screens.goals_screen

import com.example.goals.domain.utils.order.GoalOrder
import com.example.goals.presentation.screens.goals_screen.components.GoalSection

sealed class GoalsEvent {

    data class OrderChange(val goalOrder: GoalOrder): GoalsEvent()

    data class GoalSectionChange(val goalSection: GoalSection): GoalsEvent()

    object ToggleOrderSection: GoalsEvent()

}