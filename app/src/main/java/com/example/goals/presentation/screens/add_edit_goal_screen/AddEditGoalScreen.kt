package com.example.goals.presentation.screens.add_edit_goal_screen

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
import com.example.goals.presentation.components.ColorBox
import com.example.goals.presentation.components.CustomTextField
import com.example.goals.presentation.components.DatePickerDialog
import com.example.goals.presentation.components.SubGoal
import com.example.goals.presentation.ui.theme.GrayShadeLight
import com.example.goals.presentation.ui.theme.TextWhite
import com.example.goals.presentation.ui.theme.fonts
import com.example.goals.utils.EMPTY_STRING
import com.example.goals.utils.formatDate
import com.example.goals.utils.listOfColors
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditGoalScreen(
    viewModel: AddEditGoalViewModel = hiltViewModel(),
    navController: NavController,
) {
    //States
    val titleState = viewModel.goalTitle
    val contentState = viewModel.goalContent
    val deadlineState = viewModel.deadline
    val subGoalsState = viewModel.subGoals
    val goalColorState = Color(viewModel.goalColor)
    val bottomSheetTextState = viewModel.bottomSheetText
    val chosenSubGoalIndexState = viewModel.chosenSubGoalIndex

    //Context to show toast
    val context = LocalContext.current

    //Listening to OneTimeEvents
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is SingleUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is SingleUiEvent.NavigateBack -> {
                    navController.navigateUp()
                }
            }
        }
    }

    var showDialog by remember {
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
                viewModel.onEvent(AddEditGoalEvent.DeadlineChange(date))
                showDialog = false
            })
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
                    text = if (chosenSubGoalIndexState != null) stringResource(R.string.edit_subgoal)
                    else stringResource(R.string.new_subgoal),
                    style = TextStyle(
                        color = goalColorState,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = bottomSheetTextState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditGoalEvent.BottomSheetTextChanged(string))
                    },
                    textStyle = TextStyle(
                        color = goalColorState,
                        fontSize = 15.sp,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                if (chosenSubGoalIndexState != null) {
                    Icon(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = stringResource(id = R.string.delete_current_subgoal_desc),
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                            .align(Alignment.End)
                            .clickable {
                                viewModel.onEvent(AddEditGoalEvent.DeleteSubGoal)
                                bottomSheetCoroutineScope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                    )
                }
                Button(
                    onClick = {
                        viewModel.onEvent(AddEditGoalEvent.SaveSubGoal)
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
                        text = stringResource(id = R.string.save),
                        style = TextStyle(
                            color = goalColorState,
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
                    text = stringResource(id = R.string.goal),
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
                        borderColor = if (color == goalColorState) TextWhite else Color.Transparent,
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
                    style = TextStyle(
                        color = goalColorState,
                        fontFamily = fonts,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp
                    )
                )
                CustomTextField(
                    state = titleState,
                    color = goalColorState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditGoalEvent.TitleTextChange(string))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontFamily = fonts,
                        color = goalColorState,
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
                        color = goalColorState,
                        fontFamily = fonts,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp
                    )
                )
                CustomTextField(
                    state = contentState,
                    color = goalColorState,
                    onValueChange = { string ->
                        viewModel.onEvent(AddEditGoalEvent.ContentTextChange(string))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    textStyle = TextStyle(
                        fontFamily = fonts,
                        color = goalColorState,
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
                    text = stringResource(id = R.string.deadline),
                    style = TextStyle(
                        color = goalColorState,
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
                        text = deadlineState.formatDate(),
                        style = TextStyle(
                            fontFamily = fonts,
                            color = goalColorState,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
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
                    color = TextWhite,
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
                        style = TextStyle(
                            color = goalColorState,
                            fontFamily = fonts,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp
                        )
                    )
                    Icon(painter = painterResource(
                        id = R.drawable.ic_add),
                        contentDescription = stringResource(id = R.string.add_new_subgoal),
                        modifier = Modifier.size(35.dp)
                            .align(Alignment.CenterVertically)
                            .clickable {
                                viewModel.onEvent(AddEditGoalEvent.BottomSheetTextChanged(
                                    EMPTY_STRING)
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

                    subGoalsState.forEachIndexed() { i, subGoal ->
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
                                    bottomSheetCoroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                },
                            textStyle = TextStyle(
                                color = goalColorState,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fonts,
                                textDecoration = if (subGoalsState[i].isCompleted) {
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

