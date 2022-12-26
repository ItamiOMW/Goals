package com.example.goals.data.local.converters

import androidx.room.TypeConverter
import com.example.goals.domain.models.SubGoal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SubGoalConverter {

    @TypeConverter
    fun fromSubGoalListToString(value: List<SubGoal>): String {
        val gson = Gson()
        val type = object : TypeToken<List<SubGoal>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromStringToSubGoalList(value: String): List<SubGoal> {
        val gson = Gson()
        val type = object : TypeToken<List<SubGoal>>() {}.type
        return gson.fromJson(value, type)
    }

}