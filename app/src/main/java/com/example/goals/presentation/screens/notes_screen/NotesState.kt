package com.example.goals.presentation.screens.notes_screen

import com.example.goals.domain.models.Note

data class NotesState(
    val notesList: List<Note> = emptyList()
)