package com.example.goals.domain.usecases.note_usecases

import com.example.goals.domain.repository.NotesRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {

    operator fun invoke() = repository.getNotes()

}