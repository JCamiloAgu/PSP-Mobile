package com.camilo.psp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.camilo.psp.data.dao.TimeLogDao
import com.camilo.psp.data.entity.TimeLogEntity

//Debo probar esto
class TimeLogRepository(private val timeLogDao: TimeLogDao)
{

    fun getInfo(projectId: Int, phase: String): LiveData<List<TimeLogEntity?>> = timeLogDao.getTimeLog(projectId, phase)

    suspend fun insertTimeLog (timeLogEntity: TimeLogEntity){
        timeLogDao.insertTimeLog(timeLogEntity)
    }
}
