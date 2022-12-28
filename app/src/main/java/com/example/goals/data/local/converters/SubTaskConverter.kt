package com.example.goals.data.local.converters

import androidx.room.TypeConverter
import com.example.goals.domain.models.SubTask
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SubTaskConverter {

    @TypeConverter
    fun fromSubGoalListToString(value: List<SubTask>): String {
        val gson = Gson()
        val type = object : TypeToken<List<SubTask>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromStringToSubGoalList(value: String): List<SubTask> {
        val gson = Gson()
        val type = object : TypeToken<List<SubTask>>() {}.type
        return gson.fromJson(value, type)
    }
}