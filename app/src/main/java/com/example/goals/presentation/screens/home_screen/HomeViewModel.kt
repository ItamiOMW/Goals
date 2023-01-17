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
import com.example.goals.domain.usecases.task_usecases.GetTasksByDateAndCompletenessUseCase
import com.example.goals.domain.utils.order.OrderType
import com.example.goals.domain.utils.order.TaskOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTasksByDateAndCompletenessUseCase: GetTasksByDateAndCompletenessUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val completeSubTaskUseCase: CompleteSubTaskUseCase,
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private var getCompletedTasksJob: Job? = null

    private var getUncompletedTasksJob: Job? = null

    init {
        getTodaysUncompletedTasks(state.date, TaskOrder.Time(OrderType.Ascending))
        getTodaysCompletedTasks(state.date, TaskOrder.Time(OrderType.Ascending))
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ToggleOrderSection -> {
                state = state.copy(isOrderSectionVisible = !state.isOrderSectionVisible)
            }
            is HomeEvent.OnCompleteTask -> {
                completeTask(event.task)
            }
            is HomeEvent.OnCompleteSubTask -> {
                completeSubTask(event.subTask, event.task)
            }
            is HomeEvent.OrderChange -> {
                if (state.taskOrder::class == event.taskOrder::class &&
                    state.taskOrder.orderType == event.taskOrder.orderType
                ) {
                    return
                }
                getTodaysCompletedTasks(
                    state.date,
                    taskOrder = event.taskOrder
                )
                getTodaysUncompletedTasks(
                    state.date,
                    taskOrder = event.taskOrder
                )
            }
            is HomeEvent.TaskSectionChange -> {
                if (state.taskSection == event.taskSection) return
                state = state.copy(taskSection = event.taskSection)
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


    private fun getTodaysCompletedTasks(
        date: String,
        taskOrder: TaskOrder,
    ) {
        getCompletedTasksJob?.cancel()
        getCompletedTasksJob = viewModelScope.launch {
            getTasksByDateAndCompletenessUseCase(
                date = date,
                isCompleted = true,
                taskOrder = taskOrder
            ).collect { list ->
                state = state.copy(todaysCompletedTasks = list, taskOrder = taskOrder)
            }
        }
    }

    private fun getTodaysUncompletedTasks(
        date: String,
        taskOrder: TaskOrder,
    ) {
        getUncompletedTasksJob?.cancel()
        getUncompletedTasksJob = viewModelScope.launch {
            getTasksByDateAndCompletenessUseCase(
                date = date,
                isCompleted = false,
                taskOrder = taskOrder
            ).collect { list ->
                state = state.copy(todaysUncompletedTasks = list, taskOrder = taskOrder)
            }
        }
    }

}