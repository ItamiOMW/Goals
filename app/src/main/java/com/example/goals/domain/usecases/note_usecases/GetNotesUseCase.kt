package com.example.goals.domain.usecases.note_usecases

import com.example.goals.domain.models.Note
import com.example.goals.domain.repository.NotesRepository
import com.example.goals.domain.utils.order.NoteOrder
import com.example.goals.domain.utils.order.OrderType
import com.example.goals.utils.formatDateToLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {

    operator fun invoke(noteOrder: NoteOrder): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.Date -> notes.sortedBy { it.date.formatDateToLong() }
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.Date -> notes.sortedByDescending { it.date.formatDateToLong() }
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                    }
                }
            }
        }
    }

}