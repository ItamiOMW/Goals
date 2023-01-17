package com.example.goals.presentation.screens.notes_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.presentation.components.NoteCard
import com.example.goals.presentation.navigation.Destination
import com.example.goals.presentation.screens.notes_screen.components.NoteOrderSection
import com.example.goals.presentation.ui.theme.TextWhite
import com.example.goals.presentation.ui.theme.fonts


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
                color = TextWhite,
                fontFamily = fonts,
                fontSize = 25.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
            )
            Icon(
                painter = painterResource(id = R.drawable.sort),
                contentDescription = stringResource(R.string.sort_icon_desc),
                tint = TextWhite,
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    }
            )
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
            text = "${state.notesList.size} ${stringResource(R.string.notes_capitals)}",
            style = TextStyle(
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = fonts),
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
            LazyColumn() {
                items(state.notesList.size) { i ->
                    NoteCard(note = state.notesList[i], modifier = Modifier
                        .padding(20.dp)
                        .clickable {
                            navController.navigate(
                                Destination.NoteInfoScreen.route +
                                        "?${Destination.NOTE_ID_ARG}=${state.notesList[i].id}"
                            )
                        }
                    )
                    if (i == state.notesList.size - 1) {
                        Divider(modifier = Modifier.padding(30.dp))
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_notes),
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fonts
                    )
                )
            }
        }
    }
}