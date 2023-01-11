package com.example.goals.domain.repository

import com.example.goals.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(): Flow<List<Note>>

    fun getNoteById(id: Int): Flow<Note?>

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun editNote(note: Note)
}