package com.example.goals.presentation.screens.add_edit_note_screen

sealed class AddEditNoteUiEvent {

    data class ShowToast(val message: String) : AddEditNoteUiEvent()

    object NoteSaved : AddEditNoteUiEvent()
}