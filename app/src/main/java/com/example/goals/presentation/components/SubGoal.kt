package com.example.goals.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.goals.R
import com.example.goals.domain.models.SubGoal


@Composable
fun SubGoal(
    subGoal: SubGoal,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    onCheckBoxClick: (SubGoal) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            tint = textStyle.color,
            painter = painterResource(
                id = if (subGoal.isCompleted) R.drawable.checkbox
                else R.drawable.checkbox_empty
            ),
            contentDescription = stringResource(R.string.checkbox),
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    onCheckBoxClick(subGoal)
                }
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = subGoal.title,
            style = textStyle,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}