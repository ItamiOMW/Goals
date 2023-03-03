package com.example.goals.presentation.screens.home

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
import com.example.goals.presentation.screens.home.components.CompletedTasksPagerScreen
import com.example.goals.presentation.screens.home.components.TaskOrderSection
import com.example.goals.presentation.screens.home.components.UncompletedTasksPagerScreen
import com.example.goals.presentation.utils.TabItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerDefaults
import com.google.accompanist.pager.rememberPagerState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalSnapperApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state = viewModel.state

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState()

    val homeTabs = listOf(
        TabItem(
            stringResource(
                id = R.string.tasks_upcoming,
                state.todaysUncompletedTasks.size
            ),
            R.drawable.home,
            screen = {
                UncompletedTasksPagerScreen(
                    uncompletedTasks = state.todaysUncompletedTasks,
                    onCompleteTask = { task ->
                        viewModel.onEvent(HomeEvent.OnCompleteTask(task))
                    },
                    onCompleteSubTask = { task, subTask ->
                        viewModel.onEvent(HomeEvent.OnCompleteSubTask(task, subTask))
                    },
                    onTaskClicked = { task ->
                        navController.navigate(
                            Screen.TaskInfoScreen.getRouteWithArgs(
                                task.id
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
            stringResource(
                id = R.string.tasks_completed,
                state.todaysCompletedTasks.size
            ),
            R.drawable.home,
            screen = {
                CompletedTasksPagerScreen(
                    completedTasks = state.todaysCompletedTasks,
                    onCompleteTask = { task ->
                        viewModel.onEvent(HomeEvent.OnCompleteTask(task))
                    },
                    onCompleteSubTask = { task, subTask ->
                        viewModel.onEvent(HomeEvent.OnCompleteSubTask(task, subTask))
                    },
                    onTaskClicked = { task ->
                        navController.navigate(
                            Screen.TaskInfoScreen.getRouteWithArgs(
                                task.id
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

    Column() {
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
                    text = state.formattedDate,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground,
                )
                IconButton(
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        viewModel.onEvent(HomeEvent.ToggleOrderSection)
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
                TaskOrderSection(
                    onOrderChange = { taskOrder ->
                        viewModel.onEvent(HomeEvent.OrderChange(taskOrder))
                    },
                    taskOrder = state.taskOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(23.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                homeTabs.forEachIndexed { index, tabItem ->
                    Text(
                        text = tabItem.title,
                        style = MaterialTheme.typography.body2.copy(
                            color = if (pagerState.currentPage == index)
                                MaterialTheme.colors.onBackground else MaterialTheme.colors.secondaryVariant,
                        ),
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
            Spacer(modifier = Modifier.height(5.dp))
            HorizontalPager(
                count = homeTabs.size,
                state = pagerState,
                flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                modifier = Modifier.fillMaxSize()
            ) { i ->
                homeTabs[i].screen()
            }
        }
    }
}