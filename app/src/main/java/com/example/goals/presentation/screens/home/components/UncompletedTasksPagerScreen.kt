package com.example.goals.presentation.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.goals.R
import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task
import com.example.goals.presentation.components.TaskCard


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UncompletedTasksPagerScreen(
    uncompletedTasks: List<Task>,
    onCompleteTask: (Task) -> Unit,
    onCompleteSubTask: (Task, SubTask) -> Unit,
    onTaskClicked: (Task) -> Unit,
) {

    if (uncompletedTasks.isNotEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = 23.dp,
                top = 23.dp,
                start = 10.dp,
                end = 10.dp
            ),
            verticalArrangement = Arrangement.spacedBy(23.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(uncompletedTasks, key = { it.id }) { task ->
                TaskCard(
                    modifier = Modifier
                        .animateItemPlacement()
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .clickable {
                            onTaskClicked(task)
                        },
                    task = task,
                    onSubTaskIconCheckClick = { subTask, _ ->
                        onCompleteSubTask(task, subTask)
                    },
                    onTaskIconCheckClick = {
                        onCompleteTask(task)
                    },
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_scheduled_todays_tasks),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.secondaryVariant,
            )
        }

    }

}