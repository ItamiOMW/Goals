package com.example.goals.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals_table")
data class Goal(
    val title: String,
    val content: String,
    val subGoals: List<SubGoal>,
    val isReached: Boolean,
    val startTimestamp: Long,
    val endTimestamp: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)
