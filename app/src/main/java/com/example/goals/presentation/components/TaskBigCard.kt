package com.example.goals.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goals.R.drawable
import com.example.goals.R.string
import com.example.goals.domain.models.SubTask
import com.example.goals.domain.models.Task
import com.example.goals.presentation.ui.theme.fonts
import com.example.goals.utils.toTimeString


@Composable
fun TaskBigCard(
    modifier: Modifier = Modifier,
    task: Task,
    onSubTaskIconCheckClick: (SubTask, Task) -> Unit,
    onTaskIconCheckClick: (Task) -> Unit,
) {
    Box(modifier = modifier

    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    tint = Color.Black,
                    painter = painterResource(
                        id = if (task.isCompleted) drawable.checkbox
                        else drawable.checkbox_empty
                    ),
                    contentDescription = stringResource(string.checkbox),
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            onTaskIconCheckClick(task)
                        }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = task.title,
                    style = TextStyle(color = Color.Black,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fonts,
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = task.content,
                style = TextStyle(color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = fonts)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    tint = Color.Black,
                    painter = painterResource(id = drawable.ic_time),
                    contentDescription = stringResource(string.ic_time),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                val timeStart = task.scheduledTimeStart.toTimeString()
                val timeEnd = task.scheduledTimeEnd.toTimeString()
                Text(text = "$timeStart-$timeEnd",
                    style = TextStyle(color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = fonts))
            }
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                task.subTasks.forEach { subTask ->
                    SubTask(
                        subTask = subTask,
                        onCheckBoxClick = { clickedSubTask ->
                            onSubTaskIconCheckClick(clickedSubTask, task)
                        },
                        modifier = Modifier
                            .padding(top = 15.dp, bottom = 15.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}