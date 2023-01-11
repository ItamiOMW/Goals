package com.example.goals.presentation.screens.note_info_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.presentation.navigation.Destination
import com.example.goals.presentation.ui.theme.GrayShadeLight
import com.example.goals.presentation.ui.theme.TextWhite
import com.example.goals.presentation.ui.theme.fonts
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteInfoScreen(
    viewModel: NoteInfoViewModel = hiltViewModel(),
    navController: NavController,
) {

    val noteState = viewModel.currentNote
    val noteColor = noteState?.color?.let { Color(it) } ?: TextWhite

    //Context to show the toast
    val currentContext = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { uiEvent ->
            when (uiEvent) {
                is NoteInfoUiEvent.NoteDeleted -> {
                    navController.navigateUp()
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
            sheetBackgroundColor = GrayShadeLight,
            sheetState = bottomSheetState,
            sheetContent = {
                BottomSheetContentDeleteNote(
                    goalColor = noteColor,
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
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = stringResource(id = R.string.arrow_go_back_desc),
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    navController.navigateUp()
                                }
                        )
                        Text(
                            text = stringResource(R.string.note),
                            style = TextStyle(
                                color = TextWhite,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            modifier = Modifier.padding(start = 31.8.dp)
                        )
                        Row(
                            modifier = Modifier
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = stringResource(R.string.edit_goal),
                                modifier = Modifier
                                    .size(30.dp)
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        navController.navigate(
                                            route = Destination.AddEditNoteScreen.route +
                                                    "?${Destination.NOTE_ID_ARG}=${noteState.id}"
                                        )
                                    }
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.trash),
                                contentDescription = stringResource(R.string.delete_note_desc),
                                modifier = Modifier
                                    .size(28.dp)
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        bottomSheetScope.launch {
                                            bottomSheetState.show()
                                        }
                                    }
                            )
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
                        style = TextStyle(
                            color = noteColor,
                            fontFamily = fonts,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp
                        )
                    )
                    BasicText(
                        text = noteState.title,
                        style = TextStyle(
                            fontFamily = fonts,
                            color = noteColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Divider(
                        color = TextWhite,
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
                        style = TextStyle(
                            color = noteColor,
                            fontFamily = fonts,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp
                        )
                    )
                    BasicText(
                        text = noteState.content,
                        style = TextStyle(
                            fontFamily = fonts,
                            color = noteColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Divider(
                        color = TextWhite,
                    )
                }
            }
        }
    }

}

@Composable
fun BottomSheetContentDeleteNote(
    goalColor: Color,
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
            style = TextStyle(
                color = goalColor,
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row() {
            Button(
                onClick = {
                    cancelButtonClicked()
                },
                modifier = Modifier
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                )
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = TextStyle(
                        color = goalColor,
                        fontSize = 17.sp,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold
                    )
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
                    style = TextStyle(
                        color = goalColor,
                        fontSize = 17.sp,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}