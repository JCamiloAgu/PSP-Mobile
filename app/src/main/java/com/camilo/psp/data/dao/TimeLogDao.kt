package com.camilo.psp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camilo.psp.data.entity.TimeLogEntity

@Dao
interface TimeLogDao {

    @Query("SELECT * FROM timeLogs WHERE projectId = :projectId")
    fun getTimeLog(projectId: Int): LiveData<TimeLogEntity?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTimeLog(timeLogEntity: TimeLogEntity)
}