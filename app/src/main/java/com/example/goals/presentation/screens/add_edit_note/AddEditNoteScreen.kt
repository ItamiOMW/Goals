package com.example.goals.presentation.screens.add_edit_note

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goals.R
import com.example.goals.presentation.components.ColorBox
import com.example.goals.presentation.components.CustomTextField
import com.example.goals.utils.UNKNOWN_ID
import com.example.goals.utils.listOfColors


@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    noteId: Int,
    navController: NavController,
) {

    val titleState = viewModel.noteTitle
    val contentState = viewModel.noteContent
    val colorState = Color(viewModel.noteColor)

    //Context to show toast
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { uiEvent ->
            when (uiEvent) {
                is AddEditNoteUiEvent.NoteSaved -> {
                    navController.popBackStack()
                }
                is AddEditNoteUiEvent.ShowToast -> {
                    Toast.makeText(context, uiEvent.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, start = 8.dp, end = 8.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = stringResource(id = R.string.arrow_go_back_desc),
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Text(
                text = if (noteId == UNKNOWN_ID) stringResource(R.string.add_note)
                else stringResource(R.string.edit_note),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground
            )
            Icon(
                painter = painterResource(id = R.drawable.ready_mark),
                contentDescription = stringResource(R.string.save_note_desc),
                modifier = Modifier
                    .size(37.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    }
            )
        }
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOfColors.forEach { color ->
                ColorBox(
                    borderColor = if (color == colorState) MaterialTheme.colors.onBackground
                    else Color.Transparent,
                    color = color,
                    onColorBoxClick = { clickedColor ->
                        viewModel.onEvent(AddEditNoteEvent.ColorChange(clickedColor.toArgb()))
                    }
                )
            }
        }
        Column(
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.title),
                style = MaterialTheme.typography.h5,
                color = colorState
            )
            CustomTextField(
                state = titleState,
                onValueChange = { string ->
                    viewModel.onEvent(AddEditNoteEvent.TitleTextChange(string))
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.h6,
                color = colorState,
                singleLine = true
            )
        }
        Column(
            modifier = Modifier
                .wrapContentHeight(unbounded = true)
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.content),
                style = MaterialTheme.typography.h5,
                color = colorState,
            )
            CustomTextField(
                state = contentState,
                onValueChange = { string ->
                    viewModel.onEvent(AddEditNoteEvent.ContentTextChange(string))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                textStyle = MaterialTheme.typography.h6,
                color = colorState,
                singleLine = false
            )
        }
    }
}