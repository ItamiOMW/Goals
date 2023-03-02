package com.example.goals.presentation.screens.task_info

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.navigation.Screen
import com.example.goals.presentation.components.SubTask
import com.example.goals.utils.EMPTY_STRING
import com.example.goals.utils.formatDate
import com.example.goals.utils.timeSecondsToString
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskInfoScreen(
    viewModel: TaskInfoViewModel = hiltViewModel(),
    navController: NavController,
) {

    //States
    val currentTask = viewModel.currentTask
    val taskColor = currentTask?.color?.let { Color(it) } ?: MaterialTheme.colors.onBackground

    //Context to show the toast
    val currentContext = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { uiEvent ->
            when (uiEvent) {
                is TaskInfoUiEvent.TaskDeleted -> {
                    navController.popBackStack()
                }
                is TaskInfoUiEvent.ShowToast -> {
                    Toast.makeText(currentContext, uiEvent.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )

    val bottomSheetScope = rememberCoroutineScope()

    val sheetInitialContent: @Composable (() -> Unit) = { Text(EMPTY_STRING) }

    var customSheetContent by remember { mutableStateOf(sheetInitialContent) }

    if (currentTask != null) {
        ModalBottomSheetLayout(
            sheetShape = RoundedCornerShape(10.dp, 10.dp),
            sheetBackgroundColor = MaterialTheme.colors.surface,
            sheetState = bottomSheetState,
            sheetContent = {
                customSheetContent()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp, start = 8.dp, end = 8.dp, bottom = 20.dp),

                    ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = stringResource(id = R.string.arrow_go_back_desc),
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                        Text(
                            text = stringResource(R.string.task),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(start = 31.8.dp)
                        )
                        Row(
                            modifier = Modifier
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = stringResource(R.string.edit_goal),
                                modifier = Modifier
                                    .size(30.dp)
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        navController.navigate(
                                            Screen.AddEditTaskScreen.getRouteWithArgs(
                                                currentTask.id
                                            ),
                                        ) {
                                            navController.currentDestination?.id?.let {
                                                popUpTo(
                                                    it
                                                ) {
                                                    saveState = true
                                                }
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.trash),
                                contentDescription = stringResource(R.string.delete_task_desc),
                                modifier = Modifier
                                    .size(28.dp)
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        customSheetContent = {
                                            BottomSheetContentDeleteTask(
                                                taskColor = taskColor,
                                                deleteButtonClicked = {
                                                    bottomSheetScope.launch {
                                                        viewModel.onEvent(TaskInfoEvent.DeleteTask)
                                                        bottomSheetState.hide()
                                                    }
                                                },
                                                cancelButtonClicked = {
                                                    bottomSheetScope.launch {
                                                        bottomSheetState.hide()
                                                    }
                                                }
                                            )
                                        }
                                        bottomSheetScope.launch {
                                            bottomSheetState.show()
                                        }
                                    }
                            )
                        }
                    }
                }
                Text(
                    text = if (currentTask.isCompleted) stringResource(R.string.completed_title)
                    else stringResource(R.string.uncompleted_title),
                    style = MaterialTheme.typography.h5.copy(
                        textDecoration = if (currentTask.isCompleted) TextDecoration.LineThrough
                        else TextDecoration.None,
                    ),
                    color = taskColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.title),
                        style = MaterialTheme.typography.h5,
                        color = taskColor,
                    )
                    Text(
                        text = currentTask.title,
                        style = MaterialTheme.typography.h6,
                        color = taskColor,
                    )
                    Divider(
                        color = MaterialTheme.colors.onBackground,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.content),
                        style = MaterialTheme.typography.h5,
                        color = taskColor,
                    )
                    Text(
                        text = currentTask.content,
                        style = MaterialTheme.typography.h6,
                        color = taskColor,
                    )
                    Divider(
                        color = MaterialTheme.colors.onBackground,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.date),
                        style = MaterialTheme.typography.h5,
                        color = taskColor,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = currentTask.scheduledDate.formatDate(),
                            style = MaterialTheme.typography.h6,
                            color = taskColor,
                        )
                        Icon(painter = painterResource(
                            id = R.drawable.calendar),
                            contentDescription = stringResource(R.string.calendar_desc),
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(25.dp)
                        )
                    }
                    Divider(
                        color = MaterialTheme.colors.onBackground,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row() {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .shadow(5.dp, RoundedCornerShape(10.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.start_time),
                                style = MaterialTheme.typography.h5,
                                color = taskColor,
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = currentTask.scheduledTimeStart.timeSecondsToString(),
                                    style = MaterialTheme.typography.h6,
                                    color = taskColor,
                                )
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.ic_time
                                    ),
                                    contentDescription = stringResource(R.string.clock_desc),
                                    modifier = Modifier
                                        .padding(end = 5.dp)
                                        .size(25.dp)
                                )
                            }
                            Divider(
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .shadow(5.dp, RoundedCornerShape(10.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.end_time),
                                style = MaterialTheme.typography.h5,
                                color = taskColor,
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = currentTask.scheduledTimeEnd.timeSecondsToString(),
                                    style = MaterialTheme.typography.h6,
                                    color = taskColor,
                                )
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.ic_time
                                    ),
                                    contentDescription = stringResource(R.string.clock_desc),
                                    modifier = Modifier
                                        .padding(end = 5.dp)
                                        .size(25.dp)
                                )
                            }
                            Divider(
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .wrapContentHeight(unbounded = true)
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.sub_tasks),
                        style = MaterialTheme.typography.h5,
                        color = taskColor,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        currentTask.subTasks.forEachIndexed { i, subTask ->
                            SubTask(
                                subTask = subTask,
                                onCheckBoxClick = { subGoalToChange ->
                                    viewModel.onEvent(TaskInfoEvent.ChangeSubTaskCompleteness(
                                        subGoalToChange,
                                        currentTask)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(10.dp),
                                textStyle = MaterialTheme.typography.h6.copy(
                                    color = taskColor,
                                    textDecoration = if (currentTask.subTasks[i].isCompleted) {
                                        TextDecoration.LineThrough
                                    } else TextDecoration.None
                                )
                            )
                        }
                    }
                    Divider(
                        color = MaterialTheme.colors.onBackground,
                    )
                }
                Button(
                    onClick = {
                        customSheetContent = {
                            BottomSheetContentCompleteTask(
                                isTaskCompleted = currentTask.isCompleted,
                                taskColor = taskColor,
                                positiveButtonClicked = {
                                    bottomSheetScope.launch {
                                        viewModel.onEvent(TaskInfoEvent.CompleteTask)
                                        bottomSheetState.hide()
                                    }
                                },
                                cancelButtonClicked = {
                                    bottomSheetScope.launch {
                                        bottomSheetState.hide()
                                    }
                                }
                            )
                        }
                        bottomSheetScope.launch {
                            bottomSheetState.show()
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .shadow(15.dp, RoundedCornerShape(10.dp))
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = if (currentTask.isCompleted) stringResource(id = R.string.uncomplete)
                        else stringResource(R.string.complete_task),
                        style = MaterialTheme.typography.body1,
                        color = taskColor,
                    )
                }
            }
        }
    }
}


@Composable
private fun BottomSheetContentCompleteTask(
    isTaskCompleted: Boolean,
    taskColor: Color,
    positiveButtonClicked: () -> Unit,
    cancelButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isTaskCompleted) stringResource(R.string.make_task_uncompleted_question)
            else stringResource(R.string.complete_task_question),
            style = MaterialTheme.typography.h6,
            color = taskColor,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
        ) {
            Button(
                onClick = {
                    cancelButtonClicked()
                },
                modifier = Modifier.wrapContentHeight(),
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = MaterialTheme.typography.body1,
                    color = taskColor,
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    positiveButtonClicked()
                },
                modifier = Modifier
                    .wrapContentHeight(),
            ) {
                Text(
                    text = if (isTaskCompleted) stringResource(R.string.uncomplete)
                    else stringResource(R.string.complete),
                    style = MaterialTheme.typography.body1,
                    color = taskColor,
                )
            }
        }
    }
}


@Composable
private fun BottomSheetContentDeleteTask(
    taskColor: Color,
    deleteButtonClicked: () -> Unit,
    cancelButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.delete_this_task_question),
            style = MaterialTheme.typography.h6,
            color = taskColor,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row() {
            Button(
                onClick = {
                    cancelButtonClicked()
                },
                modifier = Modifier
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                )
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = MaterialTheme.typography.body1,
                    color = taskColor,
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    deleteButtonClicked()
                },
                modifier = Modifier
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                )
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    style = MaterialTheme.typography.body1,
                    color = taskColor,
                )
            }
        }
    }
}