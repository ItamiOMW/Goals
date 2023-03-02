package com.example.goals.presentation.screens.add_edit_note

sealed class AddEditNoteUiEvent {

    data class ShowToast(val message: String) : AddEditNoteUiEvent()

    object NoteSaved : AddEditNoteUiEvent()
}