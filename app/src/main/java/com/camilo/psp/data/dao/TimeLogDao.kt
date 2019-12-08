package com.camilo.psp.data.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camilo.psp.data.entity.TimeLogEntity

@Dao
interface TimeLogDao {

    @Query("SELECT * FROM timeLogs WHERE projectId = :projectId AND phase = :phase")
    fun getTimeLog(projectId: Int, phase: String): LiveData<List<TimeLogEntity?>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTimeLog(timeLogEntity: TimeLogEntity)
}