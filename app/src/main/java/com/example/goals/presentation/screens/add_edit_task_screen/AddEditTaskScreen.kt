package com.example.goals.presentation.screens.add_edit_task_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.presentation.components.*
import com.example.goals.presentation.ui.theme.GrayShadeLight
import com.example.goals.presentation.ui.theme.TextWhite
import com.example.goals.presentation.ui.theme.fonts
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
                    navController.navigateUp()
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

    Scaffold() {
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
        sheetBackgroundColor = GrayShadeLight,
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
                    style = TextStyle(
                        color = colorState,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                CustomTextField(
                    state = bottomSheetTextState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditTaskEvent.BottomSheetTextChange(string))
                    },
                    textStyle = TextStyle(
                        fontFamily = fonts,
                        color = colorState,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    color = colorState,
                    singleLine = true,
                )
                if (chosenSubTaskIndex != null) {
                    Icon(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = stringResource(id = R.string.delete_current_subtask_desc),
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .align(Alignment.End)
                            .clickable {
                                viewModel.onEvent(AddEditTaskEvent.DeleteSubTask(chosenSubTaskIndex))
                                bottomSheetCoroutineScope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                    )
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
                    colors = ButtonDefaults.buttonColors(
                        GrayShadeLight
                    )
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        style = TextStyle(
                            color = colorState,
                            fontSize = 17.sp,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold
                        )
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
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = stringResource(id = R.string.arrow_go_back_desc),
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            navController.navigateUp()
                        }
                )
                Text(
                    text = if (taskId == UNKNOWN_ID) stringResource(R.string.add_task)
                    else stringResource(R.string.edit_task),
                    style = TextStyle(
                        color = TextWhite,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ready_mark),
                    contentDescription = stringResource(id = R.string.save_goal_desc),
                    modifier = Modifier
                        .size(37.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            viewModel.onEvent(AddEditTaskEvent.SaveTask)
                        }
                )
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
                        borderColor = if (color == colorState) TextWhite else Color.Transparent,
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
                    style = TextStyle(
                        color = colorState,
                        fontFamily = fonts,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp
                    )
                )
                CustomTextField(
                    state = titleState,
                    color = colorState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditTaskEvent.TitleTextChange(string))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontFamily = fonts,
                        color = colorState,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
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
                    style = TextStyle(
                        color = colorState,
                        fontFamily = fonts,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp
                    )
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
                    textStyle = TextStyle(
                        fontFamily = fonts,
                        color = colorState,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
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
                    style = TextStyle(
                        color = colorState,
                        fontFamily = fonts,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BasicText(
                        text = dateState.formatDate(),
                        style = TextStyle(
                            fontFamily = fonts,
                            color = colorState,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
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
                    color = TextWhite,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
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
                            style = TextStyle(
                                color = colorState,
                                fontFamily = fonts,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 26.sp
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BasicText(
                                text = timeStartState.timeSecondsToString(),
                                style = TextStyle(
                                    fontFamily = fonts,
                                    color = colorState,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
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
                            color = TextWhite,
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
                            style = TextStyle(
                                color = colorState,
                                fontFamily = fonts,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 26.sp
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BasicText(
                                text = timeEndState.timeSecondsToString(),
                                style = TextStyle(
                                    fontFamily = fonts,
                                    color = colorState,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
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
                            color = TextWhite
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
                        style = TextStyle(
                            color = colorState,
                            fontFamily = fonts,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp
                        )
                    )
                    Icon(painter = painterResource(
                        id = R.drawable.ic_add
                    ),
                        contentDescription = stringResource(R.string.add_new_subtask_desc),
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.CenterVertically)
                            .clickable {
                                viewModel.onEvent(AddEditTaskEvent.SubTaskItemSelected(null))
                                viewModel.onEvent(AddEditTaskEvent.BottomSheetTextChange(EMPTY_STRING))
                                bottomSheetCoroutineScope.launch {
                                    bottomSheetState.show()
                                }
                            }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    subTasksState.forEachIndexed() { i, subTask ->
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
                            textStyle = TextStyle(
                                color = colorState,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fonts,
                                textDecoration = if (subTasksState[i].isCompleted) {
                                    TextDecoration.LineThrough
                                } else TextDecoration.None
                            )
                        )
                    }
                }
                Divider(
                    color = TextWhite,
                )
            }
        }
    }
}