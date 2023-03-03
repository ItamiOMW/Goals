package com.example.goals.presentation.screens.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.navigation.Screen
import com.example.goals.presentation.components.NoteCard
import com.example.goals.presentation.screens.notes.components.NoteOrderSection


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp, start = 23.dp, end = 23.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.notes),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground,
            )
            IconButton(
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterVertically),
                onClick = {
                    viewModel.onEvent(NotesEvent.ToggleOrderSection)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sort),
                    contentDescription = stringResource(R.string.sort_icon_desc),
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
        AnimatedVisibility(
            visible = state.isOrderSectionVisible,
        ) {
            NoteOrderSection(
                onOrderChange = { noteOrder ->
                    viewModel.onEvent(NotesEvent.OrderChange(noteOrder))
                },
                noteOrder = state.noteOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            )
        }
        Spacer(modifier = Modifier.height(23.dp))
        Text(
            text = stringResource(R.string.notes_count, state.notesList.size),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Divider(
            thickness = 0.5.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (state.notesList.isNotEmpty()) {
            LazyColumn {
                items(state.notesList, key = { it.id }) { note ->
                    NoteCard(
                        note = note,
                        modifier = Modifier
                            .animateItemPlacement()
                            .padding(20.dp)
                            .clickable {
                                navController.navigate(
                                    Screen.NoteInfoScreen.getRouteWithArgs(
                                        note.id
                                    ),
                                ) {
                                    navController.currentDestination?.id?.let {
                                        popUpTo(
                                            it
                                        ) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_notes),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.secondaryVariant,
                )
            }
        }
    }
}