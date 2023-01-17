package com.example.goals.presentation.screens.home_screen

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.presentation.components.TaskBigCard
import com.example.goals.presentation.navigation.Destination
import com.example.goals.presentation.screens.home_screen.components.TaskOrderSection
import com.example.goals.presentation.screens.home_screen.components.TaskSection
import com.example.goals.presentation.ui.theme.TextWhite
import com.example.goals.presentation.ui.theme.fonts


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state

    Column() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp, start = 23.dp, end = 23.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = state.formattedDate,
                    color = TextWhite,
                    fontFamily = fonts,
                    fontSize = 25.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                )
                Icon(
                    painter = painterResource(id = R.drawable.sort),
                    contentDescription = stringResource(R.string.sort_icon_desc),
                    tint = TextWhite,
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            viewModel.onEvent(HomeEvent.ToggleOrderSection)
                        }
                )
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
            ) {
                TaskOrderSection(
                    onOrderChange = { taskOrder ->
                        viewModel.onEvent(HomeEvent.OrderChange(taskOrder))
                    },
                    taskOrder = state.taskOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(23.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${state.todaysUncompletedTasks.size} ${stringResource(R.string.tasks_upcoming)}",
                    style = TextStyle(
                        color = if (state.taskSection == TaskSection.UncompletedTasksSection)
                            TextWhite else Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fonts
                    ),
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .clickable {
                            viewModel.onEvent(HomeEvent.TaskSectionChange(TaskSection.UncompletedTasksSection))
                        }
                )
                Text(
                    text = "${state.todaysCompletedTasks.size} ${stringResource(R.string.tasks_completed)}",
                    style = TextStyle(
                        color = if (state.taskSection == TaskSection.CompletedTasksSection)
                            TextWhite else Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fonts
                    ),
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .clickable {
                            viewModel.onEvent(HomeEvent.TaskSectionChange(TaskSection.CompletedTasksSection))
                        }
                )
            }
            Divider(
                thickness = 0.5.dp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))
            if (state.taskSection == TaskSection.UncompletedTasksSection) {
                if (state.todaysUncompletedTasks.isNotEmpty()) {
                    LazyColumn() {
                        items(state.todaysUncompletedTasks.size) { i ->
                            TaskBigCard(
                                modifier = Modifier
                                    .padding(
                                        start = 10.dp,
                                        end = 10.dp,
                                        bottom = 23.dp,
                                        top = 23.dp
                                    )
                                    .shadow(5.dp, RoundedCornerShape(10.dp))
                                    .clickable {
                                        navController.navigate(
                                            Destination.TaskInfoScreen.route +
                                                    "?${Destination.TASK_ID_ARG}=${state.todaysUncompletedTasks[i].id}"
                                        )
                                    },
                                task = state.todaysUncompletedTasks[i],
                                onSubTaskIconCheckClick = { subTask, task ->
                                    viewModel.onEvent(HomeEvent.OnCompleteSubTask(task, subTask))
                                },
                                onTaskIconCheckClick = { task ->
                                    viewModel.onEvent(HomeEvent.OnCompleteTask(task))
                                },
                            )
                            if (i == state.todaysUncompletedTasks.size - 1) {
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
                            text = stringResource(R.string.no_scheduled_todays_tasks),
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fonts
                            )
                        )
                    }
                }
            } else {
                if (state.todaysCompletedTasks.isNotEmpty()) {
                    LazyColumn() {
                        items(state.todaysCompletedTasks.size) { i ->
                            TaskBigCard(
                                modifier = Modifier
                                    .padding(
                                        start = 10.dp,
                                        end = 10.dp,
                                        bottom = 23.dp,
                                        top = 23.dp
                                    )
                                    .shadow(5.dp, RoundedCornerShape(10.dp))
                                    .clickable {
                                        navController.navigate(
                                            Destination.TaskInfoScreen.route +
                                                    "?${Destination.TASK_ID_ARG}=${state.todaysCompletedTasks[i].id}"
                                        )
                                    },
                                task = state.todaysCompletedTasks[i],
                                onSubTaskIconCheckClick = { subTask, task ->
                                    viewModel.onEvent(HomeEvent.OnCompleteSubTask(task, subTask))
                                },
                                onTaskIconCheckClick = { task ->
                                    viewModel.onEvent(HomeEvent.OnCompleteTask(task))
                                },
                            )
                            if (i == state.todaysCompletedTasks.size - 1) {
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
                            text = stringResource(R.string.no_completed_tasks_for_today),
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
    }
}