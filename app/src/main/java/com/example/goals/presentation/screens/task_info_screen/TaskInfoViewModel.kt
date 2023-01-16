package com.example.goals.presentation.screens.task_info_screen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.R
import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task
import com.example.goals.domain.usecases.task_usecases.CompleteSubTaskUseCase
import com.example.goals.domain.usecases.task_usecases.CompleteTaskUseCase
import com.example.goals.domain.usecases.task_usecases.DeleteTaskUseCase
import com.example.goals.domain.usecases.task_usecases.GetTaskByIdUseCase
import com.example.goals.presentation.navigation.Destination.Companion.TASK_ID_ARG
import com.example.goals.utils.UNKNOWN_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val completeSubTaskUseCase: CompleteSubTaskUseCase,
) : ViewModel() {

    var currentTask by mutableStateOf<Task?>(null)
        private set

    private val _eventFlow = MutableSharedFlow<TaskInfoUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Int? = null

    init {
        savedStateHandle.get<Int>(TASK_ID_ARG)?.let { taskId ->
            if (taskId != UNKNOWN_ID) {
                currentTaskId = taskId
                getTaskById(taskId)
            }
        }
    }

    fun onEvent(event: TaskInfoEvent) {
        when (event) {
            is TaskInfoEvent.DeleteTask -> {
                deleteTask(currentTask)
            }
            is TaskInfoEvent.ChangeSubTaskCompleteness -> {
                changeSubTaskCompleteness(event.subTask, event.task)
            }
            is TaskInfoEvent.CompleteTask -> {
                completeTask(currentTask) //Repository checks Task completion state and replace it with opposite value (true -> false, false -> true)
            }
        }
    }

    private fun changeSubTaskCompleteness(subTask: SubTask, task: Task) {
        viewModelScope.launch {
            completeSubTaskUseCase.invoke(subTask, task)
        }
    }

    private fun deleteTask(task: Task?) {
        viewModelScope.launch {
            task?.let {
                deleteTaskUseCase.invoke(it)
                _eventFlow.emit(TaskInfoUiEvent.ShowToast(application.getString(R.string.task_deleted)))
                _eventFlow.emit(TaskInfoUiEvent.TaskDeleted)
            }
        }
    }

    private fun completeTask(task: Task?) {
        viewModelScope.launch {
            task?.let {
                completeTaskUseCase.invoke(it)
            }
        }
    }

    private fun getTaskById(id: Int) {
        viewModelScope.launch {
            getTaskByIdUseCase.invoke(id).collectLatest { task ->
                currentTask = task
            }
        }
    }

}