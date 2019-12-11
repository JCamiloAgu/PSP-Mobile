package com.camilo.psp.data.repository

import androidx.lifecycle.LiveData
import com.camilo.psp.data.dao.DefectLogDao
import com.camilo.psp.data.entity.DefectLogEntity

class DefectLogRepository(private val defectLogDao: DefectLogDao) {

    fun getAllDefectLogs(projectId: Int): LiveData<List<DefectLogEntity?>> = defectLogDao.getAllDefectLogs(projectId)

    suspend fun insertDefectLog(defectLogEntity: DefectLogEntity){
        defectLogDao.insertDefectLog(defectLogEntity)
    }
}