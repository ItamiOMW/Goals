package com.example.goals.domain.usecases.goal_usecases

import com.example.goals.domain.models.Goal
import com.example.goals.domain.models.SubGoal
import com.example.goals.domain.repository.GoalsRepository
import javax.inject.Inject

class CompleteSubGoalUseCase @Inject constructor(
    private val repository: GoalsRepository,
) {

    suspend operator fun invoke(subGoal: SubGoal, goal: Goal) =
        repository.completeSubGoal(subGoal, goal)

}