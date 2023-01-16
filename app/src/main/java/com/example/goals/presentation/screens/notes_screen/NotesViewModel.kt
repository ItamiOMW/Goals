package com.example.goals.presentation.screens.notes_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.domain.usecases.note_usecases.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {


    private var _state by mutableStateOf(NotesState())
    val state: NotesState
        get() = _state

    init {
        getNotes()
    }

    fun onEvent(event: NotesEvent) {
        when (event) {

        }
    }

    private fun getNotes() {
        viewModelScope.launch {
            getNotesUseCase().collect { list ->
                _state = state.copy(notesList = list)
            }
        }
    }

}