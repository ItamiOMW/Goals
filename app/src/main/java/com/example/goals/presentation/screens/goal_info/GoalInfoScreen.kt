package com.example.goals.presentation.screens.goal_info

import android.widget.Toast
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
import com.example.goals.navigation.Screen.AddEditGoalScreen
import com.example.goals.presentation.components.SubGoal
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
    val goalColor = goalState?.color?.let { Color(it) } ?: MaterialTheme.colors.onBackground

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
                            text = stringResource(id = R.string.goal),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(start = 31.8.dp)
                        )
                        Row(
                            modifier = Modifier
                        ) {
                            IconButton(
                                modifier = Modifier
                                    .size(30.dp)
                                    .align(Alignment.CenterVertically),
                                onClick = {
                                    navController.navigate(
                                        AddEditGoalScreen.getRouteWithArgs(
                                            goalState.id
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
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = stringResource(R.string.edit_goal),
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))
                            IconButton(
                                modifier = Modifier
                                    .size(28.dp)
                                    .align(Alignment.CenterVertically),
                                onClick = {
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
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.trash),
                                    contentDescription = stringResource(R.string.delete_goal_desc),
                                    modifier = Modifier
                                        .size(28.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }
                Text(
                    text = if (goalState.isReached) stringResource(R.string.achieved) else stringResource(
                        R.string.not_achieved
                    ),
                    style = MaterialTheme.typography.h5.copy(
                        textDecoration = if (goalState.isReached) TextDecoration.LineThrough
                        else TextDecoration.None,
                    ),
                    color = goalColor,
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
                        color = goalColor
                    )
                    Text(
                        text = goalState.title,
                        style = MaterialTheme.typography.h6,
                        color = goalColor
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
                        color = goalColor
                    )
                    Text(
                        text = goalState.content,
                        style = MaterialTheme.typography.h5,
                        color = goalColor
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
                        text = stringResource(R.string.start_date),
                        style = MaterialTheme.typography.h5,
                        color = goalColor
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = goalState.startDate.formatDate(),
                            style = MaterialTheme.typography.h6,
                            color = goalColor
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
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.deadline),
                        style = MaterialTheme.typography.h6,
                        color = goalColor
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = goalState.endDate.formatDate(),
                            style = MaterialTheme.typography.h6,
                            color = goalColor
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
                    Text(
                        text = stringResource(id = R.string.sub_goals),
                        style = MaterialTheme.typography.h5,
                        color = goalColor
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
                                    viewModel.onEvent(
                                        GoalInfoEvent.ChangeSubGoalCompleteness(
                                            subGoalToChange,
                                            goalState
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(10.dp),
                                textStyle = MaterialTheme.typography.body1.copy(
                                    color = goalColor,
                                    textDecoration = if (goalState.subGoals[i].isCompleted) {
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
                ) {
                    Text(
                        text = if (goalState.isReached) stringResource(id = R.string.uncomplete)
                        else stringResource(R.string.complete_goal),
                        style = MaterialTheme.typography.body1,
                        color = goalColor
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
            text = if (isGoalCompleted) stringResource(R.string.make_goal_uncompleted_question)
            else stringResource(R.string.complete_goal_question),
            style = MaterialTheme.typography.h6,
            color = goalColor
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
                    style = MaterialTheme.typography.body1,
                    color = goalColor
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
                    text = if (isGoalCompleted) stringResource(R.string.uncomplete)
                    else stringResource(R.string.complete),
                    style = MaterialTheme.typography.body1,
                    color = goalColor
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
            style = MaterialTheme.typography.h6,
            color = goalColor
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
                    color = goalColor
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
                    color = goalColor
                )
            }
        }
    }
}
