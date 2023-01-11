package com.example.goals.presentation.screens.note_info_screen

sealed class NoteInfoUiEvent {

    object NoteDeleted: NoteInfoUiEvent()

    data class ShowToast(val message: String): NoteInfoUiEvent()

}