package com.example.goals.data.repository

import com.example.goals.data.local.GoalsDao
import com.example.goals.domain.models.Goal
import com.example.goals.domain.repository.GoalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalsRepositoryImpl @Inject constructor (
    private val dao: GoalsDao,
) : GoalsRepository {

    override fun getGoals(): Flow<List<Goal>> {
        return dao.getGoals()
    }

    override suspend fun getGoalById(id: Int): Goal? {
        return dao.getGoalById(id)
    }

    override suspend fun addGoal(goal: Goal) {
        dao.addGoal(goal)
    }

    override suspend fun deleteGoal(goal: Goal) {
        dao.deleteGoal(goal.id)
    }

}