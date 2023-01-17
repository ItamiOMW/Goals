package com.example.goals.presentation.screens.goals_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.domain.usecases.goal_usecases.GetGoalsByDateAndCompletenessUseCase
import com.example.goals.domain.utils.order.GoalOrder
import com.example.goals.domain.utils.order.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val getGoalsByDateAndCompletenessUseCase: GetGoalsByDateAndCompletenessUseCase,
) : ViewModel() {

    var state by mutableStateOf(GoalsState())
        private set

    init {
        getNotAchievedGoals()
        getAchievedGoals()
    }

    private var getAchievedGoalsJob: Job? = null

    private var getNotAchievedGoalsJob: Job? = null

    fun onEvent(event: GoalsEvent) {
        when (event) {
            is GoalsEvent.ToggleOrderSection -> {
                state = state.copy(isOrderSectionVisible = !state.isOrderSectionVisible)
            }
            is GoalsEvent.GoalSectionChange -> {
                state = state.copy(goalSection = event.goalSection)
            }
            is GoalsEvent.OrderChange -> {
                if (state.goalOrder::class == event.goalOrder::class &&
                    state.goalOrder.orderType == event.goalOrder.orderType
                ) {
                    return
                }
                getAchievedGoals(
                    goalOrder = event.goalOrder
                )
                getNotAchievedGoals(
                    goalOrder = event.goalOrder
                )
            }
        }
    }

    private fun getAchievedGoals(
        goalOrder: GoalOrder = GoalOrder.Date(OrderType.Ascending),
    ) {
        getAchievedGoalsJob?.cancel()
        getAchievedGoalsJob = viewModelScope.launch {
            getGoalsByDateAndCompletenessUseCase(goalOrder, true).collect { goals ->
                state = state.copy(achievedGoals = goals, goalOrder = goalOrder)
            }
        }
    }

    private fun getNotAchievedGoals(
        goalOrder: GoalOrder = GoalOrder.Date(OrderType.Ascending),
    ) {
        getNotAchievedGoalsJob?.cancel()
        getNotAchievedGoalsJob = viewModelScope.launch {
            getGoalsByDateAndCompletenessUseCase(goalOrder, false).collect { goals ->
                state = state.copy(notAchievedGoals = goals, goalOrder = goalOrder)
            }
        }
    }
}