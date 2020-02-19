package com.camilo.psp.data.repository

import androidx.lifecycle.LiveData
import com.camilo.psp.data.dao.DefectLogDao
import com.camilo.psp.data.entity.DefectLogEntity

class DefectsInPhaseRepository(private val defectLogDao: DefectLogDao) {

    fun getDefectLogList(projectId: Int): LiveData<List<DefectLogEntity?>> =
        defectLogDao.getAllDefectLogs(projectId)
}