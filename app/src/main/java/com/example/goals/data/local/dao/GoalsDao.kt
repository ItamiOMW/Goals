package com.example.goals.data.local.dao

import androidx.room.*
import com.example.goals.domain.models.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalsDao {

    @Query("SELECT * FROM goals_table")
    fun getGoals(): Flow<List<Goal>>

    @Query("SELECT * FROM goals_table WHERE id=:id LIMIT 1")
    fun getGoalById(id: Int): Flow<Goal?>

    @Query("SELECT * FROM goals_table WHERE isReached=:isCompleted")
    fun getGoalsByDateAndCompleteness(isCompleted: Boolean = false): Flow<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGoal(goal: Goal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGoal(goal: Goal)

    @Query("DELETE FROM goals_table WHERE id=:id")
    suspend fun deleteGoal(id: Int)

}