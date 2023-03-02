package com.example.goals.presentation.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.goals.presentation.screens.main.NavigationItem


@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (NavigationItem) -> Unit,
) {
    val backstackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier
            .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)),
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSecondary
    ) {
        items.forEach { item ->
            val selected = item.screen.fullRoute == backstackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = MaterialTheme.colors.onSecondary,
                unselectedContentColor = MaterialTheme.colors.secondaryVariant,
                icon = {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                badge = {
                                    Badge { Text(text = item.badgeCount.toString()) }
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = item.iconResId),
                                    contentDescription = stringResource(id = item.nameResId),
                                    tint = if (selected) MaterialTheme.colors.onSecondary
                                    else MaterialTheme.colors.secondaryVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = item.iconResId),
                                contentDescription = stringResource(id = item.nameResId),
                                tint = if (selected) MaterialTheme.colors.onSecondary
                                else MaterialTheme.colors.secondaryVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
            )
        }
    }
}