package com.example.goals.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goals.R
import com.example.goals.domain.models.SubTask
import com.example.goals.presentation.ui.theme.fonts


@Composable
fun SubTask(
    subTask: SubTask,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    onCheckBoxClick: (SubTask) -> Unit,
) {
    Row(
        modifier = modifier
            .padding(top = 15.dp, bottom = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            tint = color,
            painter = painterResource(
                id = if (subTask.isCompleted) R.drawable.checkbox
                else R.drawable.checkbox_empty
            ),
            contentDescription = stringResource(R.string.checkbox),
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    onCheckBoxClick(subTask)
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = subTask.title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = fonts,
                textDecoration = if (subTask.isCompleted) TextDecoration.LineThrough else null
            )
        )
    }
}