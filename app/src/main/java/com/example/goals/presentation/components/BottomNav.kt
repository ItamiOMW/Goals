package com.example.goals.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.goals.presentation.ui.theme.Blue
import com.example.goals.presentation.ui.theme.Gray
import com.example.goals.presentation.ui.theme.GrayShadeLight
import com.example.goals.presentation.ui.theme.PurpleLight


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
        backgroundColor = GrayShadeLight,
    ) {
        items.forEach { item ->
            val selected = item.route == backstackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Blue,
                unselectedContentColor = Gray,
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
                                    painter = painterResource(id = item.iconId),
                                    contentDescription = item.name,
                                    tint = if (selected) PurpleLight else Color.Transparent,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = item.iconId),
                                contentDescription = item.name,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
            )
        }
    }
}