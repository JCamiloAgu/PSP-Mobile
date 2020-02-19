package com.camilo.psp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.camilo.psp.data.ProjectsRoomDatabase
import com.camilo.psp.data.entity.DefectLogEntity
import com.camilo.psp.data.repository.DefectsInPhaseRepository

class DefectsInPhaseViewModel(application: Application) : AndroidViewModel(application) {

    //Data binding

    //Defects injected in Phase
    var txtDefectsPlan: MutableLiveData<String> = MutableLiveData("0")
    var txtDefectsDdl: MutableLiveData<String> = MutableLiveData("0")
    var txtDefectsCode: MutableLiveData<String> = MutableLiveData("0")
    var txtDefectsCompile: MutableLiveData<String> = MutableLiveData("0")
    var txtDefectsUt: MutableLiveData<String> = MutableLiveData("0")
    var txtDefectsPm: MutableLiveData<String> = MutableLiveData("0")

    //Percent defects injected
    var txtPercentDefectsInPlan: MutableLiveData<String> = MutableLiveData("0 %")
    var txtPercentDefectsInDdl: MutableLiveData<String> = MutableLiveData("0 %")
    var txtPercentDefectsInCode: MutableLiveData<String> = MutableLiveData("0 %")
    var txtPercentDefectsInCompile: MutableLiveData<String> = MutableLiveData("0 %")
    var txtPercentDefectsInUt: MutableLiveData<String> = MutableLiveData("0 %")
    var txtPercentDefectsInPm: MutableLiveData<String> = MutableLiveData("0 %")


    private val defectLogDao = ProjectsRoomDatabase.getDatabase(application).defectLogDao()
    private val defectLogRepository: DefectsInPhaseRepository =
        DefectsInPhaseRepository(defectLogDao)

    var projectId: Int = 1
    val defectLogData: LiveData<List<DefectLogEntity?>>? by lazy {
        defectLogRepository.getDefectLogList(
            projectId
        )
    }

    private var total = 0

    fun calculateNumberOfDefectsInPhase(list: List<DefectLogEntity?>, isInjected: Boolean) {
        var defectsPlanCount = 0
        var defectsDdlCount = 0
        var defectsCodeCount = 0
        var defectsCompileCount = 0
        var defectsUtCount = 0
        var defectsPmCount = 0

        list.forEach {
            total++
            when (if (isInjected) it?.phaseInjected else it?.phaseRemoved) {
                "PLAN" -> defectsPlanCount++
                "DDL" -> defectsDdlCount++
                "CODE" -> defectsCodeCount++
                "COMPILE" -> defectsCompileCount++
                "UT" -> defectsUtCount++
                "PM" -> defectsPmCount++
            }
        }

        txtDefectsPlan.value = defectsPlanCount.toString()
        txtDefectsDdl.value = defectsDdlCount.toString()
        txtDefectsCode.value = defectsCodeCount.toString()
        txtDefectsCompile.value = defectsCompileCount.toString()
        txtDefectsUt.value = defectsUtCount.toString()
        txtDefectsPm.value = defectsPmCount.toString()
    }

    fun calculatePercentDefectsInPhase() {
        if (total != 0) {
            txtPercentDefectsInPlan.value = getPercent(txtDefectsPlan.value!!)

            txtPercentDefectsInDdl.value = getPercent(txtDefectsDdl.value!!)

            txtPercentDefectsInCode.value = getPercent(txtDefectsCode.value!!)

            txtPercentDefectsInCompile.value = getPercent(txtDefectsCompile.value!!)

            txtPercentDefectsInUt.value = getPercent(txtDefectsUt.value!!)

            txtPercentDefectsInPm.value = getPercent(txtDefectsPm.value!!)

        }
    }

    private fun getPercent(value: String): String =  "${((100 * value.toInt()) / total)} %"
}