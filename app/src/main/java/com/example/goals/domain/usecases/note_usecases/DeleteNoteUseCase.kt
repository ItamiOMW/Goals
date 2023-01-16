package com.example.goals.domain.usecases.note_usecases

import com.example.goals.domain.models.Note
import com.example.goals.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(note: Note) = repository.deleteNote(note)

}