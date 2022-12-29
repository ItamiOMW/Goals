package com.example.goals.presentation.screens.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goals.R
import com.example.goals.presentation.components.TaskBigCard
import com.example.goals.presentation.ui.theme.TextWhite
import com.example.goals.presentation.ui.theme.fonts


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.state

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CurrentDate(date = state.date)
        TodaysTasks(viewModel = viewModel)
    }
}


@Composable
fun CurrentDate(
    modifier: Modifier = Modifier,
    date: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 90.dp, start = 23.dp, end = 23.dp)
    ) {
        Text(
            text = date,
            color = TextWhite,
            fontFamily = fonts,
            fontSize = 25.sp,
            style = TextStyle(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(2.dp))
    }
}

@Composable
fun TodaysTasks(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(23.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${state.todaysUncompletedTasks.size} ${stringResource(R.string.tasks_upcoming)}",
            style = TextStyle(color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = fonts),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Divider(
            thickness = 0.5.dp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (state.todaysUncompletedTasks.isNotEmpty()) {
            LazyColumn() {
                items(state.todaysUncompletedTasks.size) { i ->
                    TaskBigCard(
                        task = state.todaysUncompletedTasks[i],
                        onSubTaskIconCheckClick = { subTask, task ->
                            viewModel.onEvent(HomeEvent.OnCompleteSubTask(task, subTask))
                        },
                        onTaskIconCheckClick = { task ->
                            viewModel.onEvent(HomeEvent.OnCompleteTask(task))
                        }
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
    }
}
