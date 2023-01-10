package com.example.goals.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.goals.R
import com.example.goals.R.drawable
import com.example.goals.presentation.components.BottomNavigationBar
import com.example.goals.presentation.components.NavigationItem
import com.example.goals.presentation.navigation.Destination
import com.example.goals.presentation.navigation.Navigation
import com.example.goals.presentation.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            GoalsTheme {
                val navController = rememberNavController()

                val bottomSheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    skipHalfExpanded = true
                )
                val bottomSheetCoroutineScope = rememberCoroutineScope()
                ModalBottomSheetLayout(
                    sheetShape = RoundedCornerShape(10.dp, 10.dp),
                    sheetBackgroundColor = TextWhite,
                    sheetState = bottomSheetState,
                    sheetContent = {
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
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    listForBottomSheet.forEach { item ->
                                        Column(modifier = Modifier
                                            .padding(5.dp)
                                            .clickable {
                                                navController.navigate(item.route)
                                                bottomSheetCoroutineScope.launch {
                                                    bottomSheetState.hide()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = item.iconId),
                                                contentDescription = item.name,
                                                modifier = Modifier.size(30.dp),
                                                tint = Blue
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = item.name,
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 16.sp,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(30.dp))
                                FloatingActionButton(
                                    shape = CircleShape,
                                    backgroundColor = Blue,
                                    onClick = {
                                        bottomSheetCoroutineScope.launch {
                                            bottomSheetState.hide()
                                        }
                                    },
                                ) {
                                    Icon(
                                        painter = painterResource(id = drawable.cancel),
                                        contentDescription = getString(R.string.hide_bottom_sheet),
                                        tint = Color.Black,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                ) {
                    var showBottomBar by rememberSaveable { mutableStateOf(true) }
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    showBottomBar = when (navBackStackEntry?.destination?.route) {
                        Destination.HomeScreen.route -> true
                        Destination.TasksScreen.route -> true
                        Destination.GoalsScreen.route -> true
                        Destination.NotesScreen.route -> true
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
                                    backgroundColor = GrayShadeLight
                                ) {
                                    BottomNavigationBar(
                                        items = listForBottomNav,
                                        navController = navController,
                                        onItemClick = { bottomItem ->
                                            navController.navigate(bottomItem.route)
                                        }
                                    )
                                }
                            }
                        },
                        floatingActionButtonPosition = FabPosition.Center,
                        isFloatingActionButtonDocked = true,
                        floatingActionButton = {
                            if (showBottomBar) {
                                FloatingActionButton(
                                    shape = CircleShape,
                                    backgroundColor = Blue,
                                    onClick = {
                                        bottomSheetCoroutineScope.launch {
                                            bottomSheetState.show()
                                        }
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = drawable.ic_add),
                                        contentDescription = getString(R.string.main_add_button_desc),
                                        tint = Color.Black,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        }
                    ) {
                        it //Required to use it here
                        Navigation(navHostController = navController)
                    }
                }
            }
        }
    }

    companion object {

        val listForBottomSheet = listOf(
            NavigationItem(
                name = "Task",
                route = Destination.AddEditTaskScreen.route,
                iconId = drawable.task
            ),
            NavigationItem(
                name = "Goal",
                route = Destination.AddEditGoalScreen.route,
                iconId = drawable.goal
            ),
            NavigationItem(
                name = "Note",
                route = Destination.AddEditNoteScreen.route,
                iconId = drawable.edit
            )
        )

        val listForBottomNav = listOf(
            NavigationItem(
                name = "Home",
                route = Destination.HomeScreen.route,
                iconId = drawable.home,
            ),
            NavigationItem(
                name = "Tasks",
                route = Destination.TasksScreen.route,
                iconId = drawable.list_check,
            ),
            NavigationItem(
                name = "Goals",
                route = Destination.GoalsScreen.route,
                iconId = drawable.goal,
            ),
            NavigationItem(
                name = "Notes",
                route = Destination.NotesScreen.route,
                iconId = drawable.notes,
            )

        )
    }
}