package com.example.goals.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goals.R
import com.example.goals.domain.models.Goal
import com.example.goals.presentation.ui.theme.GrayShadeLight
import com.example.goals.presentation.ui.theme.TextWhite
import com.example.goals.presentation.ui.theme.fonts


@Composable
fun GoalCard(
    goal: Goal,
    onGoalCardClick: (Goal) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColorDefault: Color = GrayShadeLight,
) {
    val colorToFill = Color(goal.color)
    val amountOfCompletedSubGoals = goal.subGoals.filter { it.isCompleted }.size
    val percentageOfCompletedSubGoals =
        (amountOfCompletedSubGoals.toFloat() / goal.subGoals.size.toFloat())
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onGoalCardClick(goal)
            }
            .background(color = if (goal.isReached) colorToFill else backgroundColorDefault) //Needs when there is a Goal without SubGoals
            .drawBehind {
                drawRoundRect(
                    color = colorToFill,
                    size = Size(
                        width = this.size.width * percentageOfCompletedSubGoals,
                        height = this.size.height
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(top = 40.dp, start = 12.dp, end = 12.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = goal.title,
                style = TextStyle(
                    color = TextWhite,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textDecoration = if (goal.isReached) TextDecoration.LineThrough else null
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(6.dp))
            if (goal.subGoals.isNotEmpty()) {
                val completedTasks = goal.subGoals.filter { it.isCompleted }.size
                val completedTasksFormatted =
                    "$completedTasks ${stringResource(R.string.of)} ${goal.subGoals.size} ${
                        stringResource(R.string.completed)
                    }"
                Text(
                    text = completedTasksFormatted,
                    style = TextStyle(
                        color = Color.Gray,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

