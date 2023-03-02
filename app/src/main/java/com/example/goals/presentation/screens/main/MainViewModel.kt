package com.example.goals.presentation.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.domain.usecases.task_usecases.GetTasksByDateAndCompletenessUseCase
import com.example.goals.utils.getCurrentDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksByDateAndCompletenessUseCase: GetTasksByDateAndCompletenessUseCase
): ViewModel() {

    //Used for tasks badge
    var uncompletedTasksAmount by mutableStateOf<Int?>(null)
        private set


    init {
        getUncompletedTasksAmount()
    }

    private fun getUncompletedTasksAmount() {
        viewModelScope.launch {
            getTasksByDateAndCompletenessUseCase.invoke(
                date = getCurrentDateString(),
                isCompleted = false
            ).collect { tasks ->
                uncompletedTasksAmount = tasks.size
            }
        }
    }

}