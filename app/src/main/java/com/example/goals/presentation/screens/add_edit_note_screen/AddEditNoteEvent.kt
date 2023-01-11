package com.example.goals.presentation.screens.add_edit_note_screen

sealed class AddEditNoteEvent {

    object SaveNote: AddEditNoteEvent()

    data class ColorChange(val colorInt: Int): AddEditNoteEvent()

    data class TitleTextChange(val text: String) : AddEditNoteEvent()

    data class ContentTextChange(val text: String) : AddEditNoteEvent()

}