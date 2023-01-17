package com.example.goals.presentation.screens.notes_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goals.domain.usecases.note_usecases.GetNotesUseCase
import com.example.goals.domain.utils.order.NoteOrder
import com.example.goals.domain.utils.order.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
) : ViewModel() {


    var state by mutableStateOf(NotesState())
        private set

    init {
        getNotes()
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.OrderChange -> {
                if (state.noteOrder::class == event.noteOrder::class &&
                    state.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(noteOrder = event.noteOrder)
            }
            is NotesEvent.ToggleOrderSection -> {
                state = state.copy(isOrderSectionVisible = !state.isOrderSectionVisible)
            }
        }
    }

    private fun getNotes(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Ascending),
    ) {
        viewModelScope.launch {
            getNotesUseCase(noteOrder).collect { list ->
                state = state.copy(notesList = list, noteOrder = noteOrder)
            }
        }
    }

}