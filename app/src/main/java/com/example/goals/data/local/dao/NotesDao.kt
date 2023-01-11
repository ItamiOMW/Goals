package com.example.goals.data.local.dao

import androidx.room.*
import com.example.goals.domain.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id=:id LIMIT 1")
    fun getNoteById(id: Int): Flow<Note?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM notes_table WHERE id=:id")
    suspend fun deleteNote(id: Int)
}