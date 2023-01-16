package com.example.goals.domain.usecases.goal_usecases

import com.example.goals.domain.repository.GoalsRepository
import javax.inject.Inject

class GetGoalsUseCase @Inject constructor(
    private val repository: GoalsRepository
) {

    operator fun invoke() = repository.getGoals()

}