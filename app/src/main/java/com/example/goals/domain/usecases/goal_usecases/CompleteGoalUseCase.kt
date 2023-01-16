package com.example.goals.domain.usecases.goal_usecases

import com.example.goals.domain.models.Goal
import com.example.goals.domain.repository.GoalsRepository
import javax.inject.Inject

class CompleteGoalUseCase @Inject constructor(
    private val repository: GoalsRepository
) {

    suspend operator fun invoke(goal: Goal) = repository.completeGoal(goal)

}