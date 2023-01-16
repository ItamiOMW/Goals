package com.example.goals.domain.usecases.goal_usecases

import com.example.goals.domain.models.Goal
import com.example.goals.domain.models.GoalTitleIsEmptyException
import com.example.goals.domain.repository.GoalsRepository
import javax.inject.Inject

class AddGoalUseCase @Inject constructor(
    private val repository: GoalsRepository
) {

    suspend operator fun invoke(goal: Goal) {
        if (goal.title.isEmpty()) {
            throw GoalTitleIsEmptyException()
        }
        repository.addGoal(goal)
    }

}