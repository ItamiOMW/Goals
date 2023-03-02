package com.example.goals.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.goals.R
import com.example.goals.domain.models.Goal


@Composable
fun GoalCard(
    goal: Goal,
    modifier: Modifier = Modifier,
) {
    val colorToFill = Color(goal.color)
    val amountOfCompletedSubGoals = goal.subGoals.filter { it.isCompleted }.size
    val percentageOfCompletedSubGoals =
        (amountOfCompletedSubGoals.toFloat() / goal.subGoals.size.toFloat())
    Box(
        modifier = modifier
            .background(color = colorToFill),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(top = 30.dp, start = 9.dp, end = 9.dp, bottom = 9.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = goal.title,
                style = MaterialTheme.typography.h6.copy(
                    textDecoration = if (goal.isReached) TextDecoration.LineThrough else null,
                    textAlign = TextAlign.Center
                ),
                color = MaterialTheme.colors.primary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(6.dp))
            if (goal.subGoals.isNotEmpty()) {
                val completedTasks = goal.subGoals.filter { it.isCompleted }.size
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            R.string.completed_of_count,
                            completedTasks,
                            goal.subGoals.size
                        ),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    CircularProgressBar(
                        percentage = percentageOfCompletedSubGoals,
                        radius = 7.dp,
                        color = Color.Gray,
                        secondColor = Color.LightGray,
                        strokeWidth = 1.75.dp
                    )
                }


            }
        }
    }
}

