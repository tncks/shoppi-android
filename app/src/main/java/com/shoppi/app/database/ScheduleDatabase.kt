package com.shoppi.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shoppi.app.model.Schedule

@Database(entities = [Schedule::class], version = 1)
abstract class ScheduleDatabase: RoomDatabase() {

    abstract fun scheduleDao(): ScheduleDao

    companion object {
        private var INSTANCE: ScheduleDatabase? = null

        fun getInstance(context: Context): ScheduleDatabase? {
            if (INSTANCE == null) {
                synchronized(ScheduleDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ScheduleDatabase::class.java, "schedule")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }

}