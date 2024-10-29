package com.eminokumus.a7minutesworkoutapp

import android.app.Application
import com.eminokumus.a7minutesworkoutapp.history.WorkoutHistoryDatabase

class WorkoutApp: Application() {
    val database by lazy {
        WorkoutHistoryDatabase.getInstance(this)
    }
}