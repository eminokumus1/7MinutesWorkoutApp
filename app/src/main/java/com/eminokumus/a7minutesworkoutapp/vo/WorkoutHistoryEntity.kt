package com.eminokumus.a7minutesworkoutapp.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history-table")
data class WorkoutHistoryEntity(
    @PrimaryKey
    val date: String,

)
