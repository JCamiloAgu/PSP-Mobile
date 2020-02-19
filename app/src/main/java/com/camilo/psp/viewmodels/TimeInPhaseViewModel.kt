package com.camilo.psp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.camilo.psp.data.ProjectsRoomDatabase
import com.camilo.psp.data.entity.TimeLogEntity
import com.camilo.psp.data.repository.TimeInPhaseRepository

class TimeInPhaseViewModel(application: Application) : AndroidViewModel(application) {

    //Data binding

    //Time in Phase
    var txtTimePhasePlan: MutableLiveData<String> = MutableLiveData("0 min")
    var txtTimePhaseDdl: MutableLiveData<String> = MutableLiveData("0 min")
    var txtTimePhaseCode: MutableLiveData<String> = MutableLiveData("0 min")
    var txtTimePhaseCompile: MutableLiveData<String> = MutableLiveData("0 min")
    var txtTimePhaseUt: MutableLiveData<String> = MutableLiveData("0 min")
    var txtTimePhasePm: MutableLiveData<String> = MutableLiveData("0 min")

    //Percent time
    var txtPercentTimeInPlan: MutableLiveData<String> = MutableLiveData("0 %")
    var txtPercentTimeInDdl: MutableLiveData<String> = MutableLiveData("0 %")
    var txtPercentTimeInCode: MutableLiveData<String> = MutableLiveData("0 %")
    var txtPercentTimeInCompile: MutableLiveData<String> = MutableLiveData("0 %")
    var txtPercentTimeInUt: MutableLiveData<String> = MutableLiveData("0 %")
    var txtPercentTimeInPm: MutableLiveData<String> = MutableLiveData("0 %")



    private val timeLogDao = ProjectsRoomDatabase.getDatabase(application).timeLogDao()
    private val timeLogRepository: TimeInPhaseRepository = TimeInPhaseRepository(timeLogDao)

    var projectId: Int = 1
    val timeLogData: LiveData<List<TimeLogEntity?>>? by lazy { timeLogRepository.getTimeLogList(projectId) }

    fun getTimePercent(timeLogData: List<TimeLogEntity?>, delta: Int) : String {
        var totalMinutes = 0

        timeLogData.forEach {
            totalMinutes += it!!.delta.toInt()
        }


        return if(totalMinutes != 0 && delta != 0){
            val percent = ((100 * delta) / totalMinutes).toString()
            return "$percent %"
        }
        else
            "0 %"
    }
}