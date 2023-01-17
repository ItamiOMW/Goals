package com.example.goals.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.goals.data.local.converters.SubGoalConverter
import com.example.goals.data.local.converters.SubTaskConverter
import com.example.goals.data.local.dao.GoalsDao
import com.example.goals.data.local.dao.NotesDao
import com.example.goals.data.local.dao.TasksDao
import com.example.goals.domain.models.Goal
import com.example.goals.domain.models.Note
import com.example.goals.domain.models.Task

@Database(
    entities = [Goal::class, Task::class, Note::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(
    SubGoalConverter::class,
    SubTaskConverter::class
)
abstract class GoalsDatabase : RoomDatabase() {

    abstract fun goalsDao(): GoalsDao

    abstract fun tasksDao(): TasksDao

    abstract fun notesDao(): NotesDao

    companion object {

        const val DB_NAME = "goals.db"

    }
}