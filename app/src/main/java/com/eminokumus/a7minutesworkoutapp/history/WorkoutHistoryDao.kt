package com.eminokumus.a7minutesworkoutapp.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.eminokumus.a7minutesworkoutapp.vo.WorkoutHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutHistoryDao {

    @Insert
    suspend fun insert(workoutHistoryEntity: WorkoutHistoryEntity)

    @Query("SELECT * FROM `history-table`")
    fun fetchAllWorkouts(): Flow<List<WorkoutHistoryEntity>>
}