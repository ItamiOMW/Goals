package com.example.goals.presentation.screens.goals

import com.example.goals.domain.utils.order.GoalOrder

sealed class GoalsEvent {

    data class OrderChange(val goalOrder: GoalOrder): GoalsEvent()

    object ToggleOrderSection: GoalsEvent()

}