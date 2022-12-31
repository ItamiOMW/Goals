package com.example.goals.presentation.screens.home_screen.tasks_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goals.presentation.components.Calendar
import com.example.goals.presentation.components.TaskBigCard
import com.example.goals.presentation.ui.theme.fonts


@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel(),
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Calendar(onDateChange = {
            Log.d("test_bug", it.toString())
            viewModel.onEvent(TasksEvent.GetTasksByDate(it.toString()))
        })
        Tasks(viewModel = viewModel)
    }
}

@Composable
fun Tasks(
    viewModel: TasksViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state
    if (state.listTasksByDate.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ) {
            items(state.listTasksByDate.size) { i ->
                TaskBigCard(
                    task = state.listTasksByDate[i],
                    onSubTaskIconCheckClick = { subTask, task ->
                        viewModel.onEvent(TasksEvent.OnCompleteSubTask(task, subTask))
                    },
                    onTaskIconCheckClick = { task ->
                        viewModel.onEvent(TasksEvent.OnCompleteTask(task))
                    }
                )
                if (i == state.listTasksByDate.size - 1) {
                    Divider(modifier = Modifier.padding(23.dp))
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No scheduled tasks for this day ${viewModel.state.date}",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fonts
                )
            )
        }
    }
}