package com.example.goals.domain.usecases.goal_usecases

import com.example.goals.domain.models.Goal
import com.example.goals.domain.repository.GoalsRepository
import com.example.goals.domain.utils.order.GoalOrder
import com.example.goals.domain.utils.order.OrderType
import com.example.goals.utils.formatDateToLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGoalsByDateAndCompletenessUseCase @Inject constructor(
    private val repository: GoalsRepository,
) {

    operator fun invoke(
        goalOrder: GoalOrder = GoalOrder.Date(OrderType.Ascending),
        isCompleted: Boolean,
    ): Flow<List<Goal>> {
        return repository.getGoalsByCompleteness(isCompleted).map { goals ->
            when (goalOrder.orderType) {
                is OrderType.Ascending -> {
                    when (goalOrder) {
                        is GoalOrder.Date -> goals.sortedBy { it.startDate.formatDateToLong() }
                        is GoalOrder.Title -> goals.sortedBy { it.title.lowercase() }
                    }
                }
                is OrderType.Descending -> {
                    when (goalOrder) {
                        is GoalOrder.Date -> goals.sortedByDescending { it.startDate.formatDateToLong() }
                        is GoalOrder.Title -> goals.sortedByDescending { it.title.lowercase() }
                    }
                }
            }
        }
    }

}