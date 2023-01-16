package com.example.goals.presentation.screens.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task
import com.example.goals.domain.usecases.task_usecases.CompleteSubTaskUseCase
import com.example.goals.domain.usecases.task_usecases.CompleteTaskUseCase
import com.example.goals.domain.usecases.task_usecases.GetTasksByDateUseCase
import com.example.goals.utils.getCurrentDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTasksByDateUseCase: GetTasksByDateUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val completeSubTaskUseCase: CompleteSubTaskUseCase
) : ViewModel() {

    private var _state by mutableStateOf(HomeState())
    val state: HomeState
        get() = _state


    init {
        getTodaysUncompletedTasks(getCurrentDateString())
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnCompleteTask -> {
                completeTask(event.task)
            }
            is HomeEvent.OnCompleteSubTask -> {
                completeSubTask(event.subTask, event.task)
            }
        }
    }

    private fun completeTask(task: Task) {
        viewModelScope.launch {
            completeTaskUseCase(task)
        }
    }

    private fun completeSubTask(subTask: SubTask, task: Task) {
        viewModelScope.launch {
            completeSubTaskUseCase(subTask, task)
        }
    }

    private fun getTodaysUncompletedTasks(date: String) {
        viewModelScope.launch {
            getTasksByDateUseCase(date).collect { list ->
                _state = state.copy(todaysUncompletedTasks = list)
            }
        }
    }

}