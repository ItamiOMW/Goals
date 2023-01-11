package com.example.goals.data.repository

import android.app.Application
import com.example.goals.R
import com.example.goals.data.local.dao.NotesDao
import com.example.goals.domain.models.InvalidNoteTitleException
import com.example.goals.domain.models.Note
import com.example.goals.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NotesRepositoryImpl @Inject constructor(
    private val dao: NotesDao,
    private val application: Application
): NotesRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override fun getNoteById(id: Int): Flow<Note?> {
        return dao.getNoteById(id)
    }

    override suspend fun addNote(note: Note) {
        if (note.title.isEmpty()) {
            throw InvalidNoteTitleException(application.getString(R.string.failed_title_is_empty))
        }
        dao.addNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.id)
    }

    override suspend fun editNote(note: Note) {
        if (note.title.isEmpty()) {
            throw InvalidNoteTitleException(application.getString(R.string.failed_title_is_empty))
        }
        dao.updateNote(note)
    }

}