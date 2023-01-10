package com.example.goals.presentation.screens.goal_info_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import com.example.goals.presentation.components.SubGoal
import com.example.goals.presentation.navigation.Destination.AddEditGoalScreen
import com.example.goals.presentation.navigation.Destination.GoalInfoScreen
import com.example.goals.presentation.ui.theme.GrayShadeLight
import com.example.goals.presentation.ui.theme.TextWhite
import com.example.goals.presentation.ui.theme.fonts
import com.example.goals.utils.EMPTY_STRING
import com.example.goals.utils.formatDate
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalInfoScreen(
    viewModel: GoalInfoViewModel = hiltViewModel(),
    navController: NavController,
) {
    val goalState = viewModel.currentGoal
    val goalColor = goalState?.color?.let { Color(it) } ?: TextWhite

    //Context to show the toast
    val currentContext = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { uiEvent ->
            when (uiEvent) {
                is GoalInfoUiEvent.GoalDeleted -> {
                    navController.navigateUp()
                }
                is GoalInfoUiEvent.ShowToast -> {
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


    if (goalState != null) {
        ModalBottomSheetLayout(
            sheetShape = RoundedCornerShape(10.dp, 10.dp),
            sheetBackgroundColor = GrayShadeLight,
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
                            ),
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
                                            route = AddEditGoalScreen.route +
                                                    "?${GoalInfoScreen.GOAL_ID_ARG}=${goalState.id}"
                                        )
                                    }
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.trash),
                                contentDescription = stringResource(R.string.delete_goal_desc),
                                modifier = Modifier
                                    .size(28.dp)
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        customSheetContent = {
                                            BottomSheetContentDeleteGoal(
                                                goalColor = goalColor,
                                                deleteButtonClicked = {
                                                    bottomSheetScope.launch {
                                                        viewModel.onEvent(GoalInfoEvent.DeleteGoal)
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
                    text = if (goalState.isReached) stringResource(R.string.achieved) else stringResource(R.string.not_achieved),
                    style = TextStyle(
                        color = goalColor,
                        fontFamily = fonts,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp,
                        textDecoration = if (goalState.isReached) TextDecoration.LineThrough
                        else TextDecoration.None,
                    ),
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
                        style = TextStyle(
                            color = goalColor,
                            fontFamily = fonts,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp
                        )
                    )
                    BasicText(
                        text = goalState.title,
                        style = TextStyle(
                            fontFamily = fonts,
                            color = goalColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Divider(
                        color = TextWhite,
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
                        style = TextStyle(
                            color = goalColor,
                            fontFamily = fonts,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp
                        )
                    )
                    BasicText(
                        text = goalState.content,
                        style = TextStyle(
                            fontFamily = fonts,
                            color = goalColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Divider(
                        color = TextWhite,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.start_date),
                        style = TextStyle(
                            color = goalColor,
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
                            text = goalState.startDate.formatDate(),
                            style = TextStyle(
                                fontFamily = fonts,
                                color = goalColor,
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
                        .fillMaxWidth()
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.deadline),
                        style = TextStyle(
                            color = goalColor,
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
                            text = goalState.endDate.formatDate(),
                            style = TextStyle(
                                fontFamily = fonts,
                                color = goalColor,
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
                    Text(
                        text = stringResource(id = R.string.sub_goals),
                        style = TextStyle(
                            color = goalColor,
                            fontFamily = fonts,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp
                        )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        goalState.subGoals.forEachIndexed { i, subGoal ->
                            SubGoal(
                                subGoal = subGoal,
                                onCheckBoxClick = { subGoalToChange ->
                                    viewModel.onEvent(GoalInfoEvent.ChangeSubGoalCompleteness(
                                        subGoalToChange,
                                        goalState)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(10.dp),
                                textStyle = TextStyle(
                                    color = goalColor,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fonts,
                                    textDecoration = if (goalState.subGoals[i].isCompleted) {
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
                Button(
                    onClick = {
                        customSheetContent = {
                            BottomSheetContentCompleteGoal(
                                isGoalCompleted = goalState.isReached,
                                goalColor = goalColor,
                                positiveButtonClicked = {
                                    bottomSheetScope.launch {
                                        viewModel.onEvent(GoalInfoEvent.CompleteGoal)
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
                    colors = ButtonDefaults.buttonColors(
                        GrayShadeLight
                    )
                ) {
                    Text(
                        text = if (goalState.isReached) stringResource(id = R.string.make_uncompleted)
                        else stringResource(R.string.complete_goal),
                        style = TextStyle(
                            color = goalColor,
                            fontSize = 17.sp,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }

}


@Composable
fun BottomSheetContentCompleteGoal(
    isGoalCompleted: Boolean,
    goalColor: Color,
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
            text = if (isGoalCompleted) stringResource(R.string.make_goal_uncompleted_qustion)
            else stringResource(R.string.complete_goal_question),
            style = TextStyle(
                color = goalColor,
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
        ) {
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
                    style = TextStyle(
                        color = goalColor,
                        fontSize = 17.sp,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    positiveButtonClicked()
                },
                modifier = Modifier
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                )
            ) {
                Text(
                    text = if (isGoalCompleted) stringResource(R.string.make_uncompleted)
                    else stringResource(R.string.complete),
                    style = TextStyle(
                        color = goalColor,
                        fontSize = 17.sp,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}


@Composable
fun BottomSheetContentDeleteGoal(
    goalColor: Color,
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
            text = stringResource(R.string.delete_this_goal_question),
            style = TextStyle(
                color = goalColor,
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
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
                    style = TextStyle(
                        color = goalColor,
                        fontSize = 17.sp,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold
                    )
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
                    style = TextStyle(
                        color = goalColor,
                        fontSize = 17.sp,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
