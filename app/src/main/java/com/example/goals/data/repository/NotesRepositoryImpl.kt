package com.example.goals.data.repository

import com.example.goals.data.local.dao.NotesDao
import com.example.goals.domain.models.Note
import com.example.goals.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NotesRepositoryImpl @Inject constructor(
    private val dao: NotesDao
): NotesRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun addNote(note: Note) {
        dao.addNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.id)
    }

    override suspend fun editNote(note: Note) {
        dao.updateNote(note)
    }

}