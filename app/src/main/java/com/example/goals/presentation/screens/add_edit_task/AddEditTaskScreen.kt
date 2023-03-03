package com.example.goals.presentation.screens.add_edit_task

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.presentation.components.*
import com.example.goals.utils.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditTaskScreen(
    viewModel: AddEditTaskViewModel = hiltViewModel(),
    navController: NavController,
    taskId: Int,
) {

    //States
    val titleState = viewModel.taskTitle
    val contentState = viewModel.taskContent
    val colorState = Color(viewModel.taskColor)
    val dateState = viewModel.date
    val timeStartState = viewModel.timeStart
    val timeEndState = viewModel.timeEnd
    val subTasksState = viewModel.subTasks
    val bottomSheetTextState = viewModel.bottomSheetText
    val chosenSubTaskIndex = viewModel.chosenSubTaskIndex


    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { uiEvent ->
            when (uiEvent) {
                is AddEditTaskUiEvent.TaskSaved -> {
                    navController.popBackStack()
                }
                is AddEditTaskUiEvent.ShowToast -> {
                    Toast.makeText(context, uiEvent.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var showStartTimePickerDialog by remember {
        mutableStateOf(false)
    }

    var showEndTimePickerDialog by remember {
        mutableStateOf(false)
    }

    Scaffold {
        it
        DatePickerDialog(
            showDialog = showDialog,
            onCancelled = {
                showDialog = false
            },
            onDatePicked = { date ->
                viewModel.onEvent(AddEditTaskEvent.DateChange(date))
                showDialog = false
            })
        TimePickerDialog(
            showDialog = showStartTimePickerDialog,
            onCancelled = {
                showStartTimePickerDialog = false
            },
            onTimePicked = { time ->
                viewModel.onEvent(AddEditTaskEvent.StartTimeChange(time))
                showStartTimePickerDialog = false
            }
        )
        TimePickerDialog(
            showDialog = showEndTimePickerDialog,
            onCancelled = {
                showEndTimePickerDialog = false
            },
            onTimePicked = { time ->
                viewModel.onEvent(AddEditTaskEvent.EndTimeChange(time))
                showEndTimePickerDialog = false
            }
        )
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val bottomSheetCoroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(10.dp, 10.dp),
        sheetBackgroundColor = MaterialTheme.colors.surface,
        sheetState = bottomSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (chosenSubTaskIndex != null) stringResource(R.string.edit_subtask)
                    else stringResource(R.string.new_subtask),
                    style = MaterialTheme.typography.h6,
                    color = colorState,
                    modifier = Modifier.fillMaxWidth()
                )
                CustomTextField(
                    state = bottomSheetTextState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditTaskEvent.BottomSheetTextChange(string))
                    },
                    textStyle = MaterialTheme.typography.h6,
                    color = colorState,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
                if (chosenSubTaskIndex != null) {
                    IconButton(
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .align(Alignment.End),
                        onClick = {
                            viewModel.onEvent(AddEditTaskEvent.DeleteSubTask(chosenSubTaskIndex))
                            bottomSheetCoroutineScope.launch {
                                bottomSheetState.hide()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.trash),
                            contentDescription = stringResource(id = R.string.delete_current_subtask_desc),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(25.dp)
                        )
                    }
                }
                Button(
                    onClick = {
                        viewModel.onEvent(
                            AddEditTaskEvent.SaveSubTask(
                                bottomSheetTextState.text,
                                chosenSubTaskIndex
                            )
                        )
                        bottomSheetCoroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    },
                    modifier = Modifier.wrapContentHeight(),
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        style = MaterialTheme.typography.body1,
                        color = colorState,
                    )
                }
            }
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = stringResource(id = R.string.arrow_go_back_desc),
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
                Text(
                    text = if (taskId == UNKNOWN_ID) stringResource(R.string.add_task)
                    else stringResource(R.string.edit_task),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onBackground,
                )
                IconButton(
                    modifier = Modifier
                        .size(37.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        viewModel.onEvent(AddEditTaskEvent.SaveTask)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ready_mark),
                        contentDescription = stringResource(id = R.string.save_goal_desc),
                        modifier = Modifier
                            .size(37.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOfColors.forEach { color ->
                    ColorBox(
                        borderColor = if (color == colorState) MaterialTheme.colors.onBackground else Color.Transparent,
                        color = color,
                        onColorBoxClick = { clickedColor ->
                            viewModel.onEvent(AddEditTaskEvent.ColorChange(clickedColor.toArgb()))
                        }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .shadow(5.dp, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.title),
                    style = MaterialTheme.typography.h5,
                    color = colorState,
                )
                CustomTextField(
                    state = titleState,
                    color = colorState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditTaskEvent.TitleTextChange(string))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.h6.copy(
                        color = colorState,
                    ),
                    singleLine = true
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentHeight(unbounded = true)
                    .shadow(5.dp, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.content),
                    style = MaterialTheme.typography.h5,
                    color = colorState
                )
                CustomTextField(
                    state = contentState,
                    color = colorState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditTaskEvent.ContentTextChange(string))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    textStyle = MaterialTheme.typography.h6,
                    singleLine = false
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(5.dp, RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .clickable {
                        showDialog = true
                    }
            ) {
                Text(
                    text = stringResource(R.string.date),
                    style = MaterialTheme.typography.h5,
                    color = colorState
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = dateState.formatDate(),
                        style = MaterialTheme.typography.h6,
                        color = colorState
                    )
                    Icon(
                        painter = painterResource(
                            id = R.drawable.calendar
                        ),
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
                Row {
                    Column(
                        modifier = Modifier
                            .clickable {
                                showStartTimePickerDialog = true
                            }
                            .weight(1f)
                            .shadow(5.dp, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.start_time),
                            style = MaterialTheme.typography.h5,
                            color = colorState
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = timeStartState.timeSecondsToString(),
                                style = MaterialTheme.typography.h6,
                                color = colorState
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
                            color = MaterialTheme.colors.onBackground,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .clickable {
                                showEndTimePickerDialog = true
                            }
                            .weight(1f)
                            .shadow(5.dp, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.end_time),
                            style = MaterialTheme.typography.h5,
                            color = colorState
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = timeEndState.timeSecondsToString(),
                                style = MaterialTheme.typography.h6,
                                color = colorState
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.sub_tasks),
                        style = MaterialTheme.typography.h5,
                        color = colorState
                    )
                    IconButton(
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.CenterVertically),
                        onClick = {
                            viewModel.onEvent(AddEditTaskEvent.SubTaskItemSelected(null))
                            viewModel.onEvent(
                                AddEditTaskEvent.BottomSheetTextChange(
                                    EMPTY_STRING
                                )
                            )
                            bottomSheetCoroutineScope.launch {
                                bottomSheetState.show()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.ic_add
                            ),
                            contentDescription = stringResource(R.string.add_new_subtask_desc),
                            modifier = Modifier
                                .size(35.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    subTasksState.forEachIndexed { i, subTask ->
                        SubTask(
                            subTask = subTask,
                            onCheckBoxClick = {
                                viewModel.onEvent(AddEditTaskEvent.ChangeSubTaskCompleteness(i))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(10.dp)
                                .clickable {
                                    viewModel.onEvent(AddEditTaskEvent.SubTaskItemSelected(i))
                                    viewModel.onEvent(AddEditTaskEvent.BottomSheetTextChange(subTask.title))
                                    bottomSheetCoroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                },
                            textStyle = MaterialTheme.typography.body1.copy(
                                color = colorState,
                                textDecoration = if (subTasksState[i].isCompleted) {
                                    TextDecoration.LineThrough
                                } else TextDecoration.None
                            ),
                        )
                    }
                }
                Divider(
                    color = MaterialTheme.colors.onBackground,
                )
            }
        }
    }
}