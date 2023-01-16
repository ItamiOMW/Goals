package com.example.goals.presentation.screens.add_edit_task_screen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.R
import com.example.goals.domain.models.*
import com.example.goals.domain.usecases.task_usecases.AddTaskUseCase
import com.example.goals.domain.usecases.task_usecases.EditTaskUseCase
import com.example.goals.domain.usecases.task_usecases.GetTaskByIdUseCase
import com.example.goals.presentation.components.TextFieldState
import com.example.goals.presentation.navigation.Destination.Companion.TASK_ID_ARG
import com.example.goals.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
    private val addTaskUseCase: AddTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
) : ViewModel() {

    var taskTitle by mutableStateOf(TextFieldState())
        private set

    var taskContent by mutableStateOf(TextFieldState())
        private set

    var taskColor by mutableStateOf(listOfColors.first().toArgb())
        private set

    var subTasks by mutableStateOf(emptyList<SubTask>())
        private set

    var date by mutableStateOf(getCurrentDateString())
        private set

    var timeStart by mutableStateOf(getCurrentTimeSeconds())
        private set

    var timeEnd by mutableStateOf(getCurrentTimeSeconds())
        private set

    var bottomSheetText by mutableStateOf(TextFieldState())
        private set

    var chosenSubTaskIndex by mutableStateOf<Int?>(null)
        private set

    private val _eventFlow = MutableSharedFlow<AddEditTaskUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId = UNKNOWN_ID

    private var currentTask: Task? = null

    init {
        savedStateHandle.get<Int>(TASK_ID_ARG)?.let { taskId ->
            if (taskId != UNKNOWN_ID) {
                currentTaskId = taskId
                getTaskById(taskId)
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.SubTaskItemSelected -> {
                chosenSubTaskIndex = event.index
            }
            is AddEditTaskEvent.DateChange -> {
                date = event.date
            }
            is AddEditTaskEvent.SaveTask -> {
                saveTask(
                    currentTaskId,
                    taskTitle.text,
                    taskContent.text,
                    taskColor,
                    subTasks,
                    timeStart,
                    timeEnd,
                    isCompleted = currentTask?.isCompleted ?: false,
                    date
                )
            }
            is AddEditTaskEvent.SaveSubTask -> {
                saveSubTask(event.title, event.index)
            }
            is AddEditTaskEvent.DeleteSubTask -> {
                deleteSubTask(event.index)
            }
            is AddEditTaskEvent.TitleTextChange -> {
                taskTitle = taskTitle.copy(text = event.text, textError = null)
            }
            is AddEditTaskEvent.ContentTextChange -> {
                taskContent = taskContent.copy(text = event.text, textError = null)
            }
            is AddEditTaskEvent.ColorChange -> {
                taskColor = event.colorInt
            }
            is AddEditTaskEvent.StartTimeChange -> {
                timeStart = event.time
            }
            is AddEditTaskEvent.EndTimeChange -> {
                timeEnd = event.time
            }
            is AddEditTaskEvent.ChangeSubTaskCompleteness -> {
                changeSubTaskCompleteness(event.subTaskIndex)
            }
            is AddEditTaskEvent.BottomSheetTextChange -> {
                bottomSheetText = bottomSheetText.copy(text = event.text)
            }
        }
    }

    private fun changeSubTaskCompleteness(index: Int) {
        viewModelScope.launch {
            val newSubTaskList = subTasks.toMutableList()
            val subTask = newSubTaskList[index]
            newSubTaskList.removeAt(index)
            newSubTaskList.add(index, subTask.copy(isCompleted = !subTask.isCompleted))
            subTasks = newSubTaskList
        }
    }


    private fun deleteSubTask(index: Int?) {
        if (index != null) {
            viewModelScope.launch {
                val newSubTaskList = subTasks.toMutableList()
                newSubTaskList.removeAt(index)
                subTasks = newSubTaskList
            }
        }
    }

    private fun saveSubTask(title: String, index: Int?) {
        viewModelScope.launch {
            val subTask = SubTask(
                title.trim(),
                false
            )
            val newSubTasks = subTasks.toMutableList()
            subTasks = if (index == null) {
                //If Index is null then it's new SubGoal and we should just add it
                newSubTasks.add(subTask)
                newSubTasks
            } else {
                //Else then it's edited SubGoal and we should replace it with by index
                newSubTasks.removeAt(index)
                newSubTasks.add(index, subTask)
                newSubTasks
            }
        }
    }


    private fun saveTask(
        id: Int,
        title: String,
        content: String,
        color: Int,
        subTasks: List<SubTask>,
        timeStart: Long,
        timeEnd: Long,
        isCompleted: Boolean,
        date: String,
    ) {
        viewModelScope.launch {
            try {
                val task = Task(
                    title,
                    content,
                    color,
                    date,
                    timeStart,
                    timeEnd,
                    isCompleted,
                    subTasks,
                    id
                )
                if (id == UNKNOWN_ID) {
                    addTaskUseCase.invoke(task)
                    _eventFlow.emit(AddEditTaskUiEvent.ShowToast(application.getString(R.string.task_added)))
                } else {
                    editTaskUseCase.invoke(task)
                    _eventFlow.emit(AddEditTaskUiEvent.ShowToast(application.getString(R.string.task_edited)))
                }
                _eventFlow.emit(AddEditTaskUiEvent.TaskSaved)
            } catch (e: TaskTitleIsEmptyException) {
                taskTitle = taskTitle.copy(
                    textError = application.getString(R.string.failed_title_is_empty)
                )
                _eventFlow.emit(
                    AddEditTaskUiEvent.ShowToast(application.getString(R.string.failed_title_is_empty))
                )
            }
        }
    }


    private fun getTaskById(id: Int) {
        viewModelScope.launch {
            getTaskByIdUseCase.invoke(id).collectLatest { task ->
                if (task != null) {
                    taskTitle = taskTitle.copy(text = task.title)
                    taskContent = taskContent.copy(text = task.content)
                    taskColor = task.color
                    subTasks = task.subTasks
                    timeStart = task.scheduledTimeStart
                    timeEnd = task.scheduledTimeEnd
                }
            }
        }
    }
}