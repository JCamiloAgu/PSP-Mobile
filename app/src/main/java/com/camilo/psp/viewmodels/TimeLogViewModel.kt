package com.camilo.psp.viewmodels

import android.app.Application
import android.os.Build
import androidx.lifecycle.*
import com.camilo.psp.data.ProjectsRoomDatabase
import com.camilo.psp.data.entity.TimeLogEntity
import com.camilo.psp.data.repository.TimeLogRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// TODO = Creo que debo cambiar la mayoria de elementos de LiveData

class TimeLogViewModel(application: Application) : AndroidViewModel(application) {

    // Repositorio
    private val timeLogDao = ProjectsRoomDatabase.getDatabase(application).timeLogDao()
    private val timeLogRepository: TimeLogRepository = TimeLogRepository(timeLogDao)

    var projectId: Int = 1
    val timeLogInfo: LiveData<List<TimeLogEntity?>> by lazy {  timeLogRepository.getInfo(projectId) }


    //En estos campos se almacena la fecha en Date o DateTime para hacer el cálculo del deltaTime
    lateinit var timeStartDate: Any
    lateinit var timeStopDate: Any

    // Estas variables establecen la visibilidad de los botones de Start y de Stop
    val isEnabledBtnStart: MutableLiveData<Boolean> = MutableLiveData(true)
    val isEnabledBtnStop: MutableLiveData<Boolean> = MutableLiveData(false)
    val isEnabledBtnReg: MutableLiveData<Boolean> = MutableLiveData(false)

    //Estas variables establecen el ENABLED de los comments y los Interruptions
    val isEnabledEditText: MutableLiveData<Boolean> = MutableLiveData(true)

    //Textos para el layout
    val txtStart: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val txtInterruption: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val txtStop: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val txtDelta: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val txtComments: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    private fun setActualTime(toAffect: MutableLiveData<String>)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = LocalDateTime.now()
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm:ss")
            val final = formatter.format(localDateTime)
            final.let { toAffect.value = final }
            if (toAffect == txtStart)
                timeStartDate = localDateTime
            else
                timeStopDate = localDateTime
        }
        else
        {
            val pattern = "d MMM yyyy HH:mm:ss"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val final = simpleDateFormat.format(Date())
            final.let { toAffect.value = final }
            if (toAffect == txtStart)
                timeStartDate = Date()
            else
                timeStopDate = Date()
        }
    }

    fun setDeltaTime(timeStart: Any, timeStop: Any, interruption: Int)
    {
        if (!isEnabledBtnStart.value!! && !isEnabledBtnStop.value!!) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                timeStart as LocalDateTime
                timeStop as LocalDateTime
                txtDelta.value = ((timeStop.minute - timeStart.minute) - interruption).toString()
            } else {
                timeStart as Date
                timeStop as Date

                val timeDiff: Long = (timeStart.time - timeStop.time) - interruption

                val segMilli: Long = 1000
                val minutesMilli = segMilli * 60
                val timeDiffMinutes = timeDiff / minutesMilli
                txtDelta.value = timeDiffMinutes.toString()
            }
        }
    }
    fun setInputsEnabled(isAllDisable: Boolean)
    {
        if(isAllDisable) {
            isEnabledBtnStart.value = false
            isEnabledBtnStop.value = false
            isEnabledBtnReg.value = false

            isEnabledEditText.value = false
        }
        else
        {
            isEnabledBtnStart.value = true
            isEnabledBtnStop.value = false
            isEnabledBtnReg.value = false

            isEnabledEditText.value = true
        }
    }


    fun btnHidden(toAffect: MutableLiveData<String>) {
        if (toAffect == txtStart) {
            setActualTime(toAffect)
            isEnabledBtnStart.value = false
            isEnabledBtnStop.value = true

        }
        else {
            setActualTime(toAffect)
            isEnabledBtnStop.value = false
            isEnabledBtnReg.value = true
        }
    }

    //TODO = DEBO CAMBIAR ESTE NOMBRE POR ALGO MAS SIGNIFICATIVO
    fun strategySelected(phase: String){
        if (timeLogInfo.value != null) {
            for (info in timeLogInfo.value!!) {
                if (info!!.phase == phase) {
                    setInputsEnabled(true)
                    setTextInputs(info)
                    break
                } else {
                    setInputsEnabled(false)
                    setTextInputs(null)
                }
            }
        }
    }

    private fun setTextInputs(info: TimeLogEntity?){
        if (info != null) {
            txtStart.value = info.start
            txtInterruption.value = info.interruption.toString()
            txtStop.value = info.stop
            txtDelta.value = info.delta
            txtComments.value = info.comments
        }
        else
        {
            txtStart.value = ""
            txtInterruption.value = ""
            txtStop.value = ""
            txtDelta.value = ""
            txtComments.value = ""
        }
    }

    fun insertTimeLog(timeLogEntity: TimeLogEntity) = viewModelScope.launch {
        timeLogRepository.insertTimeLog(timeLogEntity)
    }


}