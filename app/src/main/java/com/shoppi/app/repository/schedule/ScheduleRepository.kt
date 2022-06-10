package com.shoppi.app.repository.schedule

import android.app.Application
import androidx.lifecycle.LiveData
import com.shoppi.app.database.ScheduleDao
import com.shoppi.app.database.ScheduleDatabase
import com.shoppi.app.model.Schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleRepository(application: Application) {

    private val scheduleDatabase = ScheduleDatabase.getInstance(application)!!
    private val scheduleDao: ScheduleDao = scheduleDatabase.scheduleDao()
    private val schedules: LiveData<List<Schedule>> = scheduleDao.getAll()

    fun getAll(): LiveData<List<Schedule>> {
        return schedules
    }

    fun insert(schedule: Schedule) {
        try {
            val thread = Thread {
                scheduleDao.insert(schedule)
            }
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(schedule: Schedule) {
        try {
            val thread = Thread {
                scheduleDao.delete(schedule)
            }
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun cleardropping() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {
                    scheduleDao.cleardropping()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    fun refreshupdating() {
//        CoroutineScope(Dispatchers.IO).launch {
//            withContext(Dispatchers.IO) {
//                scheduleDao.cleardropping()
//            }
//        }
//    }

}