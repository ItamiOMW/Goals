package com.example.goals.presentation.screens.notes

import com.example.goals.domain.models.Note
import com.example.goals.domain.utils.order.NoteOrder
import com.example.goals.domain.utils.order.OrderType

data class NotesState(
    val notesList: List<Note> = emptyList(),
    val isOrderSectionVisible: Boolean = false,
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Ascending),
)