package com.example.goals.domain.usecases.note_usecases

import com.example.goals.domain.repository.NotesRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NotesRepository
) {

    operator fun invoke(id: Int) = repository.getNoteById(id)

}