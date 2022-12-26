package com.example.goals.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.goals.domain.models.Goal

@Database(
    entities = [Goal::class],
    version = 1
)
abstract class GoalsDatabase : RoomDatabase() {

    abstract fun goalsDao(): GoalsDao

    companion object {

        const val DB_NAME = "goals.db"

    }
}