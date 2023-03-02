package com.example.goals.presentation.screens.add_edit_goal

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
import com.example.goals.utils.EMPTY_STRING
import com.example.goals.utils.UNKNOWN_ID
import com.example.goals.utils.formatDate
import com.example.goals.utils.listOfColors
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditGoalScreen(
    viewModel: AddEditGoalViewModel = hiltViewModel(),
    goalId: Int,
    navController: NavController,
) {
    //States
    val titleState = viewModel.goalTitle
    val contentState = viewModel.goalContent
    val deadlineState = viewModel.deadline
    val subGoalsState = viewModel.subGoals
    val goalColorState = Color(viewModel.goalColor)
    val bottomSheetTextState = viewModel.bottomSheetText
    val chosenSubGoalIndex = viewModel.chosenSubGoalIndex

    //Context to show toast
    val context = LocalContext.current

    //Listening to OneTimeEvents
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AddEditGoalUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is AddEditGoalUiEvent.GoalSaved -> {
                    navController.popBackStack()
                }
            }
        }
    }

    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }

    Scaffold {
        it
        DatePickerDialog(
            showDialog = showDatePickerDialog,
            onCancelled = {
                showDatePickerDialog = false
            },
            onDatePicked = { date ->
                viewModel.onEvent(AddEditGoalEvent.DeadlineChange(date))
                showDatePickerDialog = false
            })
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
                    text = if (chosenSubGoalIndex != null) stringResource(R.string.edit_subgoal)
                    else stringResource(R.string.new_subgoal),
                    style = MaterialTheme.typography.h6,
                    color = goalColorState,
                    modifier = Modifier.fillMaxWidth()
                )
                CustomTextField(
                    state = bottomSheetTextState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditGoalEvent.BottomSheetTextChanged(string))
                    },
                    textStyle = MaterialTheme.typography.h6,
                    color = goalColorState,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                if (chosenSubGoalIndex != null) {
                    Icon(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = stringResource(id = R.string.delete_current_subgoal_desc),
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .align(Alignment.End)
                            .clickable {
                                viewModel.onEvent(AddEditGoalEvent.DeleteSubGoal(chosenSubGoalIndex))
                                bottomSheetCoroutineScope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                    )
                }
                Button(
                    onClick = {
                        viewModel.onEvent(
                            AddEditGoalEvent.SaveSubGoal(
                                bottomSheetTextState.text,
                                chosenSubGoalIndex
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
                        color = goalColorState,
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
                            navController.popBackStack()
                        }
                )
                Text(
                    text = if (goalId == UNKNOWN_ID) stringResource(R.string.add_goal)
                    else stringResource(id = R.string.edit_goal),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary,
                )
                Icon(
                    painter = painterResource(id = R.drawable.ready_mark),
                    contentDescription = stringResource(id = R.string.save_goal_desc),
                    modifier = Modifier
                        .size(37.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            viewModel.onEvent(AddEditGoalEvent.SaveGoal)
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
                        borderColor = if (color == goalColorState) MaterialTheme.colors.onBackground
                        else Color.Transparent,
                        color = color,
                        onColorBoxClick = { clickedColor ->
                            viewModel.onEvent(AddEditGoalEvent.ColorChange(clickedColor.toArgb()))
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
                    color = goalColorState,
                )
                CustomTextField(
                    state = titleState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditGoalEvent.TitleTextChange(string))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.h5,
                    color = goalColorState,
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
                    color = goalColorState,
                )
                CustomTextField(
                    state = contentState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditGoalEvent.ContentTextChange(string))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    textStyle = MaterialTheme.typography.h5,
                    color = goalColorState,
                    singleLine = false
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(5.dp, RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .clickable {
                        showDatePickerDialog = true
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.deadline),
                    style = MaterialTheme.typography.h5,
                    color = goalColorState,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = deadlineState.formatDate(),
                        style = MaterialTheme.typography.h6,
                        color = goalColorState,
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
                    .wrapContentHeight(unbounded = true)
                    .shadow(5.dp, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.sub_goals),
                        style = MaterialTheme.typography.h5,
                        color = goalColorState,
                    )
                    Icon(painter = painterResource(
                        id = R.drawable.ic_add
                    ),
                        contentDescription = stringResource(id = R.string.add_new_subgoal),
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.CenterVertically)
                            .clickable {
                                viewModel.onEvent(AddEditGoalEvent.SubGoalItemSelected(null))
                                viewModel.onEvent(
                                    AddEditGoalEvent.BottomSheetTextChanged(
                                        EMPTY_STRING
                                    )
                                )
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
                    subGoalsState.forEachIndexed { i, subGoal ->
                        SubGoal(
                            subGoal = subGoal,
                            onCheckBoxClick = {
                                viewModel.onEvent(AddEditGoalEvent.ChangeSubGoalCompleteness(i))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(10.dp)
                                .clickable {
                                    viewModel.onEvent(AddEditGoalEvent.SubGoalItemSelected(i))
                                    viewModel.onEvent(
                                        AddEditGoalEvent.BottomSheetTextChanged(
                                            subGoal.title
                                        )
                                    )
                                    bottomSheetCoroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                },
                            textStyle = MaterialTheme.typography.body1.copy(
                                color = goalColorState,
                                textDecoration = if (subGoalsState[i].isCompleted) {
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

