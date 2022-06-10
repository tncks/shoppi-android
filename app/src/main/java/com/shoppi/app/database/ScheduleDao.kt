package com.shoppi.app.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shoppi.app.model.Schedule

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedule")
    fun getAll(): LiveData<List<Schedule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)

    @Query("DELETE FROM schedule")
    fun cleardropping()

}