package com.camilo.psp.data.repository

import androidx.lifecycle.LiveData
import com.camilo.psp.data.dao.TimeLogDao
import com.camilo.psp.data.entity.TimeLogEntity

class TimeInPhaseRepository(private val timeLogDao: TimeLogDao) {

    fun getTimeLogList(projectId: Int): LiveData<List<TimeLogEntity?>> = timeLogDao.getTimeLog(projectId)

}