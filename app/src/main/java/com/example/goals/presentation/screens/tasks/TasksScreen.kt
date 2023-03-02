package com.example.goals.presentation.screens.tasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.navigation.Screen
import com.example.goals.presentation.components.CalendarView
import com.example.goals.presentation.components.TaskCard


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        CalendarView(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colors.secondary),
            onDateChanged = { date ->
                viewModel.onEvent(TasksEvent.GetTasksByDate(date))
            },
            date = viewModel.state.date
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (state.listTasksByDate.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier,
                contentPadding = PaddingValues(
                    start = 30.dp,
                    end = 30.dp,
                    top = 30.dp,
                    bottom = 30.dp
                ),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                items(state.listTasksByDate, key = { it.id }) { task ->
                    TaskCard(
                        task = task,
                        onSubTaskIconCheckClick = { subTask, _ ->
                            viewModel.onEvent(TasksEvent.OnCompleteSubTask(task, subTask))
                        },
                        onTaskIconCheckClick = {
                            viewModel.onEvent(TasksEvent.OnCompleteTask(task))
                        },
                        modifier = Modifier
                            .animateItemPlacement()
                            .shadow(5.dp, RoundedCornerShape(10.dp))
                            .clickable {
                                navController.navigate(
                                    Screen.TaskInfoScreen.getRouteWithArgs(
                                        task.id
                                    ),
                                ) {
                                    navController.currentDestination?.id?.let {
                                        popUpTo(it) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_scheduled_tasks_this_day),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
}