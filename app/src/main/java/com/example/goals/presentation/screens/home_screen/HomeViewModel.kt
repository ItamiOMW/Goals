package com.example.goals.presentation.screens.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task
import com.example.goals.domain.repository.TasksRepository
import com.example.goals.utils.getCurrentDateLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tasksRepository: TasksRepository,
) : ViewModel() {

    private var _state by mutableStateOf(HomeState())
    val state: HomeState
        get() = _state


    init {
        getTodaysUncompletedTasks(getCurrentDateLong())
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
            tasksRepository.completeTask(task)
        }
    }

    private fun completeSubTask(subTask: SubTask, task: Task) {
        viewModelScope.launch {
            tasksRepository.completeSubTask(subTask, task)
        }
    }

    private fun getTodaysUncompletedTasks(date: Long) {
        viewModelScope.launch {
            tasksRepository.getTasksByDate(date).collect { list ->
                _state = state.copy(todaysUncompletedTasks = list)
            }
        }
    }

}