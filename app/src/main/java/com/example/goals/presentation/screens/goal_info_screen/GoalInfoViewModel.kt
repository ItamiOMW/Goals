package com.example.goals.presentation.screens.goal_info_screen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.R
import com.example.goals.domain.models.Goal
import com.example.goals.domain.models.SubGoal
import com.example.goals.domain.repository.GoalsRepository
import com.example.goals.presentation.navigation.Destination
import com.example.goals.presentation.navigation.Destination.Companion.GOAL_ID_ARG
import com.example.goals.utils.UNKNOWN_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalInfoViewModel @Inject constructor(
    private val repository: GoalsRepository,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
) : ViewModel() {

    var currentGoal by mutableStateOf<Goal?>(null)
        private set

    private val _eventFlow = MutableSharedFlow<GoalInfoUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentGoalId: Int? = null

    init {
        savedStateHandle.get<Int>(GOAL_ID_ARG)?.let { goalId ->
            if (goalId != UNKNOWN_ID) {
                currentGoalId = goalId
                getGoalById(goalId)
            }
        }
    }

    fun onEvent(event: GoalInfoEvent) {
        when (event) {
            is GoalInfoEvent.DeleteGoal -> {
                deleteGoal(currentGoal)
            }
            is GoalInfoEvent.ChangeSubGoalCompleteness -> {
                changeSubGoalCompleteness(event.subGoal, event.goal)
            }
            is GoalInfoEvent.CompleteGoal -> {
                completeGoal(currentGoal) //Repository checks Goal completion state and replace it with opposite value (true -> false, false -> true)
            }
        }
    }

    private fun changeSubGoalCompleteness(subGoal: SubGoal, goal: Goal) {
        viewModelScope.launch {
            repository.completeSubGoal(subGoal, goal)
        }
    }

    private fun deleteGoal(goal: Goal?) {
        viewModelScope.launch {
            goal?.let {
                repository.deleteGoal(it)
                _eventFlow.emit(GoalInfoUiEvent.ShowToast(application.getString(R.string.goal_deleted)))
                _eventFlow.emit(GoalInfoUiEvent.GoalDeleted)
            }
        }
    }

    private fun completeGoal(goal: Goal?) {
        viewModelScope.launch {
            goal?.let {
                repository.completeGoal(goal)
            }
        }
    }

    private fun getGoalById(id: Int) {
        viewModelScope.launch {
            repository.getGoalById(id).collectLatest { goal ->
                currentGoal = goal
            }
        }
    }
}