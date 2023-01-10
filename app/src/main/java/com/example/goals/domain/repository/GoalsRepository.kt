package com.example.goals.domain.repository

import com.example.goals.domain.models.Goal
import com.example.goals.domain.models.SubGoal
import kotlinx.coroutines.flow.Flow

interface GoalsRepository {

    fun getGoals(): Flow<List<Goal>>

    fun getGoalById(id: Int): Flow<Goal?>

    suspend fun addGoal(goal: Goal)

    suspend fun deleteGoal(goal: Goal)

    suspend fun completeGoal(goal: Goal)

    suspend fun completeSubGoal(subGoal: SubGoal, goal: Goal)

    suspend fun editGoal(goal: Goal)

}