package com.example.goals.presentation.screens.add_edit_note_screen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.R
import com.example.goals.domain.models.Note
import com.example.goals.domain.models.NoteTitleIsEmptyException
import com.example.goals.domain.usecases.note_usecases.AddNoteUseCase
import com.example.goals.domain.usecases.note_usecases.EditNoteUseCase
import com.example.goals.domain.usecases.note_usecases.GetNoteByIdUseCase
import com.example.goals.presentation.components.TextFieldState
import com.example.goals.presentation.navigation.Destination.Companion.NOTE_ID_ARG
import com.example.goals.utils.UNKNOWN_ID
import com.example.goals.utils.getCurrentDateString
import com.example.goals.utils.listOfColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
) : ViewModel() {

    var noteTitle by mutableStateOf(
        TextFieldState(hint = application.getString(R.string.enter_title))
    )
        private set

    var noteContent by mutableStateOf(
        TextFieldState(hint = application.getString(R.string.enter_content))
    )
        private set

    var noteColor by mutableStateOf(listOfColors.first().toArgb())
        private set

    private val _eventFlow = MutableSharedFlow<AddEditNoteUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int = UNKNOWN_ID

    private var currentNote: Note? = null

    init {
        savedStateHandle.get<Int>(NOTE_ID_ARG)?.let { noteId ->
            if (noteId != UNKNOWN_ID) {
                currentNoteId = noteId
                getNoteById(noteId)
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.SaveNote -> {
                saveNote(
                    currentNoteId,
                    noteTitle.text,
                    noteContent.text,
                    getCurrentDateString(),
                    noteColor
                )
            }
            is AddEditNoteEvent.TitleTextChange -> {
                noteTitle = noteTitle.copy(text = event.text)
            }
            is AddEditNoteEvent.ContentTextChange -> {
                noteContent = noteContent.copy(text = event.text)
            }
            is AddEditNoteEvent.ColorChange -> {
                noteColor = event.colorInt
            }
        }
    }

    private fun saveNote(
        id: Int,
        title: String,
        content: String,
        date: String,
        color: Int,
    ) {
        viewModelScope.launch {
            try {
                val note = Note(title, content, date, color, id)
                if (id == UNKNOWN_ID) {
                    addNoteUseCase(note) //If id == UNKNOWN_ID then note is new and should be added
                    _eventFlow.emit(AddEditNoteUiEvent.ShowToast(application.getString(R.string.note_added)))
                } else {
                    editNoteUseCase(note) //If id != UNKNOWN_ID then goal exists and should be edited
                    _eventFlow.emit(AddEditNoteUiEvent.ShowToast(application.getString(R.string.note_edited)))
                }
                _eventFlow.emit(AddEditNoteUiEvent.NoteSaved)
            } catch (e: NoteTitleIsEmptyException) {
                _eventFlow.emit(AddEditNoteUiEvent.ShowToast(application.getString(R.string.failed_title_is_empty)))
                noteTitle = noteTitle.copy(textError = application.getString(R.string.failed_title_is_empty))
            }
        }
    }

    private fun getNoteById(id: Int) {
        viewModelScope.launch {
            getNoteByIdUseCase(id).collectLatest { note ->
                if (note != null) {
                    noteTitle = noteTitle.copy(text = note.title)
                    noteContent = noteContent.copy(text = note.content)
                    noteColor = note.color
                    currentNote = note
                }
            }
        }
    }
}