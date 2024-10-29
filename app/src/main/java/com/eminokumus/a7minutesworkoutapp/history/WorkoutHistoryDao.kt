package com.eminokumus.a7minutesworkoutapp.history

import androidx.room.Dao
import androidx.room.Insert
import com.eminokumus.a7minutesworkoutapp.vo.WorkoutHistoryEntity

@Dao
interface WorkoutHistoryDao {

    @Insert
    suspend fun insert(workoutHistoryEntity: WorkoutHistoryEntity)
}