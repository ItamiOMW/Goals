package com.example.goals.presentation.screens.note_info

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.navigation.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteInfoScreen(
    viewModel: NoteInfoViewModel = hiltViewModel(),
    navController: NavController,
) {

    val noteState = viewModel.currentNote
    val noteColor = noteState?.color?.let { Color(it) } ?: MaterialTheme.colors.onBackground

    //Context to show the toast
    val currentContext = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { uiEvent ->
            when (uiEvent) {
                is NoteInfoUiEvent.NoteDeleted -> {
                    navController.popBackStack()
                }
                is NoteInfoUiEvent.ShowToast -> {
                    Toast.makeText(currentContext, uiEvent.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    if (noteState != null) {

        val bottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = false
        )

        val bottomSheetScope = rememberCoroutineScope()

        ModalBottomSheetLayout(
            sheetShape = RoundedCornerShape(10.dp, 10.dp),
            sheetBackgroundColor = MaterialTheme.colors.surface,
            sheetState = bottomSheetState,
            sheetContent = {
                BottomSheetContentDeleteNote(
                    noteColor = noteColor,
                    deleteButtonClicked = {
                        bottomSheetScope.launch {
                            viewModel.onEvent(NoteInfoEvent.DeleteGoal)
                            bottomSheetState.hide()
                        }
                    }, cancelButtonClicked = {
                        bottomSheetScope.launch {
                            bottomSheetState.hide()
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp, start = 8.dp, end = 8.dp, bottom = 20.dp),

                    ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.CenterVertically),
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_left),
                                contentDescription = stringResource(id = R.string.arrow_go_back_desc),
                                modifier = Modifier
                                    .size(25.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Text(
                            text = stringResource(R.string.note),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(start = 31.8.dp)
                        )
                        Row(
                            modifier = Modifier
                        ) {
                            IconButton(
                                modifier = Modifier
                                    .size(30.dp)
                                    .align(Alignment.CenterVertically),
                                onClick = {
                                    navController.navigate(
                                        Screen.AddEditNoteScreen.getRouteWithArgs(
                                            noteState.id
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
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = stringResource(R.string.edit_goal),
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            IconButton(
                                modifier = Modifier
                                    .size(28.dp)
                                    .align(Alignment.CenterVertically),
                                onClick = {
                                bottomSheetScope.launch {
                                    bottomSheetState.show()
                                }
                            }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.trash),
                                    contentDescription = stringResource(R.string.delete_note_desc),
                                    modifier = Modifier
                                        .size(28.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.title),
                        style = MaterialTheme.typography.h6,
                        color = noteColor
                    )
                    Text(
                        text = noteState.title,
                        style = MaterialTheme.typography.h6,
                        color = noteColor
                    )
                    Divider(
                        color = MaterialTheme.colors.onBackground,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.content),
                        style = MaterialTheme.typography.h5,
                        color = noteColor
                    )
                    Text(
                        text = noteState.content,
                        style = MaterialTheme.typography.h6,
                        color = noteColor
                    )
                    Divider(
                        color = MaterialTheme.colors.onBackground,
                    )
                }
            }
        }
    }

}

@Composable
private fun BottomSheetContentDeleteNote(
    noteColor: Color,
    deleteButtonClicked: () -> Unit,
    cancelButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.delete_note_question),
            style = MaterialTheme.typography.h6,
            color = noteColor
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row() {
            Button(
                onClick = {
                    cancelButtonClicked()
                },
                modifier = Modifier
                    .wrapContentHeight(),
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = MaterialTheme.typography.body1,
                    color = noteColor
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    deleteButtonClicked()
                },
                modifier = Modifier
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                )
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    style = MaterialTheme.typography.body1,
                    color = noteColor
                )
            }
        }
    }
}