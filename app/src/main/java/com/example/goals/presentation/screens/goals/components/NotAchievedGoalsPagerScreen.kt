package com.example.goals.presentation.screens.goals.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.goals.R
import com.example.goals.domain.models.Goal
import com.example.goals.presentation.components.GoalCard


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotAchievedGoalsPagerScreen(
    notAchievedGoals: List<Goal>,
    onGoalClicked: (Goal) -> Unit,
) {
    if (notAchievedGoals.isNotEmpty()) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(
                bottom = 55.dp,
                start = 7.5.dp,
                end = 7.5.dp,
                top = 7.5.dp
            ),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(notAchievedGoals, key = { it.id }) { goal ->
                GoalCard(
                    modifier = Modifier
                        .animateItemPlacement()
                        .fillMaxSize()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            onGoalClicked(goal)
                        },
                    goal = goal,
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_goals),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.secondaryVariant
            )
        }
    }
}