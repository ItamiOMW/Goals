package com.example.goals.presentation.screens.goals

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.navigation.Screen
import com.example.goals.presentation.screens.goals.components.AchievedGoalsPagerScreen
import com.example.goals.presentation.screens.goals.components.GoalOrderSection
import com.example.goals.presentation.screens.goals.components.NotAchievedGoalsPagerScreen
import com.example.goals.presentation.utils.TabItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerDefaults
import com.google.accompanist.pager.rememberPagerState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalSnapperApi::class)
@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state = viewModel.state

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState()

    val goalsTabs = listOf(
        TabItem(
            title = stringResource(
                id = R.string.goals_in_progress,
                state.notAchievedGoals.size
            ),
            R.drawable.goal,
            screen = {
                NotAchievedGoalsPagerScreen(
                    notAchievedGoals = state.notAchievedGoals,
                    onGoalClicked = { goal ->
                        navController.navigate(
                            Screen.GoalInfoScreen.getRouteWithArgs(
                                goal.id
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
            }
        ),
        TabItem(
            title = stringResource(
                id = R.string.goals_achieved,
                state.achievedGoals.size
            ),
            R.drawable.goal,
            screen = {
                AchievedGoalsPagerScreen(
                    achievedGoals = state.achievedGoals,
                    onGoalClicked = { goal ->
                        navController.navigate(
                            Screen.GoalInfoScreen.getRouteWithArgs(
                                goal.id
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
            }
        )
    )


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
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                IconButton(
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        viewModel.onEvent(GoalsEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.sort),
                        contentDescription = stringResource(R.string.sort_icon_desc),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
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
                    goalsTabs.forEachIndexed { index, tabItem ->
                        Text(
                            text = tabItem.title,
                            style = MaterialTheme.typography.body2,
                            color = if (index == pagerState.currentPage)
                                MaterialTheme.colors.onBackground else MaterialTheme.colors.secondaryVariant,
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .clickable {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                        )
                    }

                }
                Divider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalPager(
                    count = goalsTabs.size,
                    state = pagerState,
                    flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                    modifier = Modifier.fillMaxSize()
                ) { i ->
                    goalsTabs[i].screen()
                }
            }
        }
    }
}