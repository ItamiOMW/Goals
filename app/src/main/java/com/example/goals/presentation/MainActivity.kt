package com.example.goals.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.goals.R.drawable
import com.example.goals.presentation.components.BottomNavItem
import com.example.goals.presentation.components.BottomNavigationBar
import com.example.goals.presentation.navigation.Destination
import com.example.goals.presentation.navigation.Navigation
import com.example.goals.presentation.ui.theme.GoalsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            GoalsTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = Destination.HomeScreen.route,
                                    iconId = drawable.home,
                                ),
                                BottomNavItem(
                                    name = "Tasks",
                                    route = Destination.TasksScreen.route,
                                    iconId = drawable.list_check,
                                ),
                                BottomNavItem(
                                    name = "Goals",
                                    route = Destination.GoalsScreen.route,
                                    iconId = drawable.goal,
                                )

                            ),
                            navController = navController,
                            onItemClick = { item -> navController.navigate(item.route) },
                        )
                    }
                ) {
                    it //Required to use it here
                    Navigation(navHostController = navController)
                }
            }
        }
    }
}

