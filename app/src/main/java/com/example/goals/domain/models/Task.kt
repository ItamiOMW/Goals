package com.example.goals.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
data class Task(
    val title: String,
    val content: String,
    val color: Int,
    val scheduledDateTime: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)
