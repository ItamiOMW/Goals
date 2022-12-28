package com.example.goals.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.goals.utils.UNKNOWN_ID

@Entity(tableName = "tasks_table")
data class Task(
    val title: String,
    val content: String,
    val colorId: Int,
    val scheduledDate: Long,
    val scheduledTimeStart: Long,
    val scheduledTimeEnd: Long,
    val isCompleted: Boolean,
    val subTasks: List<SubTask>,
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNKNOWN_ID,
)
