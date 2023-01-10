package com.example.goals.presentation.screens.goals_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.goals.presentation.ui.theme.TextWhite
import com.example.goals.presentation.ui.theme.fonts


@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel = hiltViewModel(),
    navController: NavController,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        GoalsText()
        Goals(state = viewModel.state, navController = navController)
    }
}


@Composable
fun GoalsText(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.goals_title),
        style = TextStyle(fontWeight = FontWeight.Bold,
            fontFamily = fonts,
            fontSize = 25.sp,
            color = TextWhite),
        modifier = modifier.padding(top = 90.dp, start = 23.dp, end = 23.dp),
    )
    Spacer(modifier = Modifier.height(2.dp))
}


@Composable
fun Goals(
    modifier: Modifier = Modifier,
    state: GoalsState,
    navController: NavController,
) {
    Column(
        modifier = modifier.padding(top = 23.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "${state.goals.size} ${stringResource(R.string.goals)}",
            style = TextStyle(fontWeight = FontWeight.SemiBold,
                fontFamily = fonts,
                fontSize = 14.sp,
                color = Color.Gray),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 23.dp, end = 23.dp),
        )
        Spacer(modifier = Modifier.height(2.dp))
        Divider(thickness = 0.5.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp, end = 23.dp))
        Spacer(modifier = Modifier.height(10.dp))
        if (state.goals.isNotEmpty()) {
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.SpaceEvenly,
                contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp),
                modifier = Modifier.padding(bottom = 55.dp)) {
                items(state.goals.size) { i ->
                    GoalCard(goal = state.goals[i], onGoalCardClick = { goal ->
                        navController.navigate(
                            Destination.GoalInfoScreen.route +
                                "?${Destination.GoalInfoScreen.GOAL_ID_ARG}=${goal.id}"
                        )
                    })
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.no_goals),
                    style = TextStyle(color = Color.Gray,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fonts),
                    modifier = Modifier.padding(23.dp))
            }
        }
    }
}

