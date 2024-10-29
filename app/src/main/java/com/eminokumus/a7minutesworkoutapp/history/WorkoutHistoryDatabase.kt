package com.eminokumus.a7minutesworkoutapp.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eminokumus.a7minutesworkoutapp.vo.WorkoutHistoryEntity

@Database(entities = [WorkoutHistoryEntity::class], version = 1)
abstract class WorkoutHistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): WorkoutHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: WorkoutHistoryDatabase? = null

        fun getInstance(context: Context): WorkoutHistoryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WorkoutHistoryDatabase::class.java,
                        "history_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance

            }
        }
    }
}