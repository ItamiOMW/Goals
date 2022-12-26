package com.example.goals.domain.repository

import com.example.goals.domain.models.Goal
import kotlinx.coroutines.flow.Flow

interface GoalsRepository {

    fun getGoals(): Flow<List<Goal>>

    suspend fun getGoalById(id: Int): Goal?

    suspend fun addGoal(goal: Goal)

    suspend fun deleteGoal(goal: Goal)
}