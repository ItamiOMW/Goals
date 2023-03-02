package com.example.goals.presentation.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.goals.R
import com.example.goals.navigation.AppNavigation
import com.example.goals.navigation.Screen
import com.example.goals.presentation.screens.main.components.BottomNavigationBar
import com.example.goals.presentation.ui.theme.White
import com.example.goals.utils.UNKNOWN_ID
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {


    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(10.dp, 10.dp),
        sheetBackgroundColor = White,
        sheetState = bottomSheetState,
        sheetContent = {
            MainBottomSheetContent(
                onNavigate = { navItem ->
                    val route = when (
                        navItem.screen
                    ) {
                        is Screen.AddEditGoalScreen -> {
                            navItem.screen.getRouteWithArgs(UNKNOWN_ID)
                        }
                        is Screen.AddEditNoteScreen -> {
                            navItem.screen.getRouteWithArgs(UNKNOWN_ID)
                        }
                        is Screen.AddEditTaskScreen -> {
                            navItem.screen.getRouteWithArgs(UNKNOWN_ID)
                        }
                        else -> throw Exception(
                            "Undefined NavigationItem, define NavigationItem in MainBottomSheetContent"
                        )
                    }
                    navController.navigate(
                        route = route
                    ) {
                        navController.currentDestination?.id?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }

                },
                onHide = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        }
    ) {
        var showBottomBar by rememberSaveable { mutableStateOf(true) }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        showBottomBar = when (navBackStackEntry?.destination?.route) {
            Screen.HomeScreen.fullRoute -> true
            Screen.TasksScreen.fullRoute -> true
            Screen.GoalsScreen.fullRoute -> true
            Screen.NotesScreen.fullRoute -> true
            else -> false
        }
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    BottomAppBar(
                        modifier = Modifier
                            .height(60.dp)
                            .clip(RoundedCornerShape(35.dp, 35.dp, 0.dp, 0.dp)),
                        cutoutShape = CircleShape,
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        BottomNavigationBar(
                            items = listOf(
                                NavigationItem.Home(
                                    mainViewModel.uncompletedTasksAmount ?: 0
                                ),
                                NavigationItem.Tasks,
                                NavigationItem.Goals,
                                NavigationItem.Notes
                            ),
                            navController = navController,
                            onItemClick = { bottomItem ->
                                navController.navigate(bottomItem.screen.fullRoute) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                if (showBottomBar) {
                    BottomBarFAB(
                        onBottomSheetShow = {
                            coroutineScope.launch {
                                bottomSheetState.show()
                            }
                        }
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier.padding(it)
            ) {
                AppNavigation(navHostController = navController)
            }
        }
    }

}


@Composable
private fun BottomBarFAB(
    onBottomSheetShow: () -> Unit,
) {
    FloatingActionButton(
        shape = CircleShape,
        backgroundColor = MaterialTheme.colors.onSecondary,
        onClick = {
            onBottomSheetShow()
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = stringResource(R.string.main_add_button_desc),
            tint = Color.Black,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
private fun MainBottomSheetContent(
    onNavigate: (NavigationItem) -> Unit,
    onHide: () -> Unit,
) {

    val bottomSheetNavItems = listOf(
        NavigationItem.AddTask,
        NavigationItem.AddGoal,
        NavigationItem.AddNote,
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                bottomSheetNavItems.forEach { item ->
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                onNavigate(item)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = stringResource(id = item.nameResId),
                            modifier = Modifier.size(30.dp),
                            tint = MaterialTheme.colors.onSecondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(id = item.nameResId),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            FloatingActionButton(
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.onSecondary,
                onClick = {
                    onHide()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = stringResource(R.string.hide_bottom_sheet),
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}