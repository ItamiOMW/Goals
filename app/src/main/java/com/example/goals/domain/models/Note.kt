package com.example.goals.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.goals.utils.UNKNOWN_ID

@Entity(tableName = "notes_table")
data class Note(
    val title: String,
    val content: String,
    val date: String,
    val colorId: Int,
    @PrimaryKey
    val id: Int = UNKNOWN_ID
) {
}