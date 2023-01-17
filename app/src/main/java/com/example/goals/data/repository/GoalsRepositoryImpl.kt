package com.example.goals.data.repository

import com.example.goals.data.local.dao.GoalsDao
import com.example.goals.domain.models.Goal
import com.example.goals.domain.models.SubGoal
import com.example.goals.domain.repository.GoalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalsRepositoryImpl @Inject constructor(
    private val dao: GoalsDao,
) : GoalsRepository {

    override fun getGoals(): Flow<List<Goal>> {
        return dao.getGoals()
    }

    override fun getGoalById(id: Int): Flow<Goal?> {
        return dao.getGoalById(id)
    }

    override fun getGoalsByCompleteness(
        isCompleted: Boolean,
    ): Flow<List<Goal>> {
        return dao.getGoalsByDateAndCompleteness(isCompleted = isCompleted)
    }

    override suspend fun addGoal(goal: Goal) {
        dao.addGoal(goal)
    }

    override suspend fun deleteGoal(goal: Goal) {
        dao.deleteGoal(goal.id)
    }

    override suspend fun completeGoal(goal: Goal) {
        val goalToUpdate = goal.copy(isReached = !goal.isReached, subGoals = goal.subGoals.map {
            it.copy(isCompleted = !goal.isReached)
        })
        dao.updateGoal(goalToUpdate)
    }

    override suspend fun completeSubGoal(subGoal: SubGoal, goal: Goal) {
        val subGoalIndex = goal.subGoals.indexOf(subGoal)
        val newList = goal.subGoals.toMutableList()
        //Replace objects
        newList.removeAt(subGoalIndex)
        newList.add(subGoalIndex, subGoal.copy(isCompleted = !subGoal.isCompleted))
        //Updated goal
        val updatedGoal = goal.copy(subGoals = newList)
        dao.updateGoal(updatedGoal)
    }

    override suspend fun editGoal(goal: Goal) {
        dao.updateGoal(goal)
    }

}