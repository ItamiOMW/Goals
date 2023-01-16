package com.example.goals.presentation.screens.goals_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.domain.usecases.goal_usecases.GetGoalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor (
    private val getGoalsUseCase: GetGoalsUseCase,
) : ViewModel() {

    private var _state by mutableStateOf(GoalsState())
    val state: GoalsState
        get() = _state

    init {
        getGoals()
    }

    fun onEvent(event: GoalsEvent) {

    }

    private fun getGoals() {
        viewModelScope.launch {
            getGoalsUseCase().collect { list ->
                _state = state.copy(goals = list)
            }
        }
    }
}