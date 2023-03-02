package com.example.goals.presentation.screens.note_info

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.R
import com.example.goals.domain.models.Note
import com.example.goals.domain.usecases.note_usecases.DeleteNoteUseCase
import com.example.goals.domain.usecases.note_usecases.GetNoteByIdUseCase
import com.example.goals.navigation.Screen.Companion.NOTE_ID_ARG
import com.example.goals.utils.UNKNOWN_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteInfoViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application
) : ViewModel() {

    var currentNote by mutableStateOf<Note?>(null)

    private val _eventFlow = MutableSharedFlow<NoteInfoUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentGoalId: Int? = null

    init {
        savedStateHandle.get<Int>(NOTE_ID_ARG)?.let { noteId ->
            if (noteId != UNKNOWN_ID) {
                currentGoalId = noteId
                getNoteById(noteId)
            }
        }
    }

    fun onEvent(event: NoteInfoEvent) {
        when (event) {
            is NoteInfoEvent.DeleteGoal -> {
                deleteNote(currentNote)
            }
        }
    }

    private fun deleteNote(note: Note?) {
        if (note != null) {
            viewModelScope.launch {
                deleteNoteUseCase(note)
                _eventFlow.emit(NoteInfoUiEvent.ShowToast(application.getString(R.string.note_deleted)))
                _eventFlow.emit(NoteInfoUiEvent.NoteDeleted)
            }
        }
    }

    private fun getNoteById(id: Int) {
        viewModelScope.launch {
            getNoteByIdUseCase(id).collectLatest { note ->
                if (note != null) {
                    currentNote = note
                }
            }
        }
    }

}