package com.example.goals.domain.usecases.note_usecases

import com.example.goals.domain.models.Note
import com.example.goals.domain.models.NoteTitleIsEmptyException
import com.example.goals.domain.repository.NotesRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(note: Note) {
        if (note.title.isEmpty()) {
            throw NoteTitleIsEmptyException()
        }
        repository.addNote(note)
    }

}