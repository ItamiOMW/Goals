package com.example.goals.presentation.screens.tasks_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.presentation.components.Calendar
import com.example.goals.presentation.components.TaskBigCard
import com.example.goals.presentation.navigation.Destination
import com.example.goals.presentation.ui.theme.fonts


@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel(),
    navController: NavController,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Calendar(onDateChange = {
            viewModel.onEvent(TasksEvent.GetTasksByDate(it.toString()))
        })
        val state = viewModel.state
        if (state.listTasksByDate.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
            ) {
                items(state.listTasksByDate.size) { i ->
                    TaskBigCard(
                        task = state.listTasksByDate[i],
                        onSubTaskIconCheckClick = { subTask, task ->
                            viewModel.onEvent(TasksEvent.OnCompleteSubTask(task, subTask))
                        },
                        onTaskIconCheckClick = { task ->
                            viewModel.onEvent(TasksEvent.OnCompleteTask(task))
                        },
                        modifier = Modifier
                            .padding(33.dp)
                            .shadow(5.dp, RoundedCornerShape(10.dp))
                            .clickable {
                                navController.navigate(
                                    Destination.TaskInfoScreen.route +
                                            "?${Destination.TASK_ID_ARG}=${state.listTasksByDate[i].id}"
                                )
                            },
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
}