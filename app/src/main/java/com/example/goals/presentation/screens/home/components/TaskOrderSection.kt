package com.example.goals.presentation.screens.home.components


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.goals.R
import com.example.goals.domain.utils.order.OrderType
import com.example.goals.domain.utils.order.TaskOrder
import com.example.goals.presentation.components.DefaultRadioButton

@Composable
fun TaskOrderSection(
    modifier: Modifier = Modifier,
    taskOrder: TaskOrder,
    onOrderChange: (TaskOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.title),
                selected = taskOrder is TaskOrder.Title,
                onSelect = { onOrderChange(TaskOrder.Title(taskOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.time),
                selected = taskOrder is TaskOrder.Time,
                onSelect = { onOrderChange(TaskOrder.Time(taskOrder.orderType)) }
            )

        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.ascending),
                selected = taskOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(taskOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.descending),
                selected = taskOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(taskOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}