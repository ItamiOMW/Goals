package com.example.goals.presentation.screens.goal_info

import com.example.goals.domain.models.Goal
import com.example.goals.domain.models.SubGoal

sealed class GoalInfoEvent {

    object CompleteGoal : GoalInfoEvent()

    object DeleteGoal : GoalInfoEvent()

    data class ChangeSubGoalCompleteness(val subGoal: SubGoal, val goal: Goal) : GoalInfoEvent()
}