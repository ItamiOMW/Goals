package com.example.goals.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.goals.data.local.converters.SubGoalConverter
import com.example.goals.data.local.converters.SubTaskConverter
import com.example.goals.domain.models.Goal
import com.example.goals.domain.models.Task

@Database(
    entities = [Goal::class, Task::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(
    SubGoalConverter::class,
    SubTaskConverter::class
)
abstract class GoalsDatabase : RoomDatabase() {

    abstract fun goalsDao(): GoalsDao

    abstract fun tasksDao(): TasksDao

    companion object {

        const val DB_NAME = "goals.db"

    }
}