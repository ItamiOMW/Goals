package com.example.goals.domain.usecases.goal_usecases

import com.example.goals.domain.repository.GoalsRepository
import javax.inject.Inject

class GetGoalByIdUseCase @Inject constructor(
    private val repository: GoalsRepository
) {

    operator fun invoke(id: Int) = repository.getGoalById(id)

}