package com.camilo.psp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.camilo.psp.data.entity.DefectLogEntity

@Dao
interface DefectLogDao {
    @Query("SELECT * FROM defectLogs WHERE projectId = :projectId")
    fun getAllDefectLogs(projectId: Int): LiveData<List<DefectLogEntity?>>

    @Insert
    suspend fun insertDefectLog(defectLogEntity: DefectLogEntity)
}