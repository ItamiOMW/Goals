package com.example.goals.presentation.screens.goals_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.goals.presentation.components.GoalCard
import com.example.goals.presentation.navigation.Destination
import com.example.goals.presentation.navigation.Destination.Companion.GOAL_ID_ARG
import com.example.goals.presentation.screens.goals_screen.components.GoalOrderSection
import com.example.goals.presentation.screens.goals_screen.components.GoalSection
import com.example.goals.presentation.ui.theme.TextWhite
import com.example.goals.presentation.ui.theme.fonts


@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state
    Column {
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
                    text = stringResource(R.string.goals_title),
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
                            viewModel.onEvent(GoalsEvent.ToggleOrderSection)
                        }
                )
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
            ) {
                GoalOrderSection(
                    onOrderChange = { goalOrder ->
                        viewModel.onEvent(GoalsEvent.OrderChange(goalOrder))
                    },
                    goalOrder = state.goalOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 23.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${state.notAchievedGoals.size} ${stringResource(R.string.goals_in_progress)}",
                        style = TextStyle(
                            color = if (state.goalSection == GoalSection.NotAchievedTasksSection)
                                TextWhite else Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = fonts
                        ),
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .clickable {
                                viewModel.onEvent(GoalsEvent.GoalSectionChange(GoalSection.NotAchievedTasksSection))
                            }
                    )
                    Text(
                        text = "${state.achievedGoals.size} ${stringResource(R.string.goals_achieved)}",
                        style = TextStyle(
                            color = if (state.goalSection == GoalSection.AchievedGoalsSection)
                                TextWhite else Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = fonts
                        ),
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .clickable {
                                viewModel.onEvent(GoalsEvent.GoalSectionChange(GoalSection.AchievedGoalsSection))
                            }
                    )
                }
                Divider(
                    thickness = 0.5.dp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (state.goalSection == GoalSection.AchievedGoalsSection) {
                    if (state.achievedGoals.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(bottom = 55.dp)
                        ) {
                            items(state.achievedGoals.size) { i ->
                                GoalCard(goal = state.achievedGoals[i], onGoalCardClick = { goal ->
                                    navController.navigate(
                                        Destination.GoalInfoScreen.route +
                                                "?$GOAL_ID_ARG=${goal.id}"
                                    )
                                })
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.no_achieved_goals),
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
                    if (state.notAchievedGoals.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(bottom = 55.dp)
                        ) {
                            items(state.notAchievedGoals.size) { i ->
                                GoalCard(
                                    goal = state.notAchievedGoals[i],
                                    onGoalCardClick = { goal ->
                                        navController.navigate(
                                            Destination.GoalInfoScreen.route +
                                                    "?$GOAL_ID_ARG=${goal.id}"
                                        )
                                    })
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.no_goals),
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
}