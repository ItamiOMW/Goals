package com.example.goals.presentation.screens.home_screen.tasks_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task
import com.example.goals.domain.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TasksRepository,
) : ViewModel() {

    private var _state by mutableStateOf(TasksState())
    val state: TasksState
        get() = _state


    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.GetTasksByDate -> {
                _state = state.copy(date = event.date)
                getTasks(event.date)
            }
            is TasksEvent.OnCompleteSubTask -> {
                completeSubTask(event.subTask, event.task)
            }
            is TasksEvent.OnCompleteTask -> {
                completeTask(event.task)
            }
        }
    }

    private fun completeTask(task: Task) {
        viewModelScope.launch {
            repository.completeTask(task)
        }
    }

    private fun completeSubTask(subTask: SubTask, task: Task) {
        viewModelScope.launch {
            repository.completeSubTask(subTask, task)
        }
    }

    private fun getTasks(date: String) {
        viewModelScope.launch {
            repository.getTasksByDate(date).collect { list ->
                _state = state.copy(listTasksByDate = list)
            }
        }
    }

}