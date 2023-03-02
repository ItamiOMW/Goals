package com.example.goals.presentation.screens.notes

import com.example.goals.domain.utils.order.NoteOrder

sealed class NotesEvent {


    data class OrderChange(val noteOrder: NoteOrder): NotesEvent()

    object ToggleOrderSection: NotesEvent()

}