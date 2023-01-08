package com.example.goals.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.goals.utils.UNKNOWN_ID

@Entity(tableName = "goals_table")
data class Goal(
    val title: String,
    val content: String,
    val subGoals: List<SubGoal>,
    val isReached: Boolean,
    val startDate: String,
    val endDate: String,
    val color: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNKNOWN_ID,
)

class InvalidGoalTitleException(message: String) : Exception(message)
