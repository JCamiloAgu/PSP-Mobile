package com.camilo.psp.data.repository

import androidx.lifecycle.LiveData
import com.camilo.psp.data.dao.TimeLogDao
import com.camilo.psp.data.entity.TimeLogEntity

//Debo probar esto
class TimeLogRepository(private val timeLogDao: TimeLogDao, projectId: Int)
{
    val timeLogInfo: LiveData<TimeLogEntity?> by lazy { timeLogDao.getTimeLog(projectId) }

    suspend fun insertTimeLog (timeLogEntity: TimeLogEntity){
        timeLogDao.insertTimeLog(timeLogEntity)
    }
}
