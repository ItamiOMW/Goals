package com.example.goals.presentation.screens.note_info

sealed class NoteInfoUiEvent {

    object NoteDeleted: NoteInfoUiEvent()

    data class ShowToast(val message: String): NoteInfoUiEvent()

}