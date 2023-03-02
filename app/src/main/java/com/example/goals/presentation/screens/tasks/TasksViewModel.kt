package com.example.goals.presentation.screens.tasks

import android.util.Log
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val completeSubTaskUseCase: CompleteSubTaskUseCase,
    private val getTasksByDateUseCase: GetTasksByDateUseCase,
) : ViewModel() {

    var state by mutableStateOf(TasksState())
        private set

    private var getTasksJob: Job? = null

    init {
        getTasksByDate(state.date)
    }

    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.GetTasksByDate -> {
                state = state.copy(date = event.date)
                getTasksByDate(event.date)
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
            completeTaskUseCase(task)
        }
    }

    private fun completeSubTask(subTask: SubTask, task: Task) {
        viewModelScope.launch {
            completeSubTaskUseCase(subTask, task)
        }
    }

    private fun getTasksByDate(date: String) {
        getTasksJob?.cancel()
        getTasksJob = viewModelScope.launch {
            getTasksByDateUseCase(date = date).collect { list ->
                Log.d("test_bug", date)
                state = state.copy(listTasksByDate = list)
            }
        }
    }

}