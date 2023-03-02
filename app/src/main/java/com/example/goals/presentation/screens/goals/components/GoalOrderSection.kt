package com.example.goals.presentation.screens.goals.components


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.goals.R
import com.example.goals.domain.utils.order.GoalOrder
import com.example.goals.domain.utils.order.OrderType
import com.example.goals.presentation.components.DefaultRadioButton

@Composable
fun GoalOrderSection(
    modifier: Modifier = Modifier,
    goalOrder: GoalOrder,
    onOrderChange: (GoalOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.title),
                selected = goalOrder is GoalOrder.Title,
                onSelect = { onOrderChange(GoalOrder.Title(goalOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.date),
                selected = goalOrder is GoalOrder.Date,
                onSelect = { onOrderChange(GoalOrder.Date(goalOrder.orderType)) }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.ascending),
                selected = goalOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(goalOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.descending),
                selected = goalOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(goalOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}