package com.camilo.psp.viewmodels

import android.app.Application
import android.os.Build
import android.os.SystemClock
import android.widget.Chronometer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.camilo.psp.data.ProjectsRoomDatabase
import com.camilo.psp.data.entity.DefectLogEntity
import com.camilo.psp.data.repository.DefectLogRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DefectLogViewModel(application: Application) : AndroidViewModel(application)
{
    //TODO = Instanciar el repositorio
    private val defectLogDao = ProjectsRoomDatabase.getDatabase(application).defectLogDao()
    private val defectLogRepository = DefectLogRepository(defectLogDao)

    var projectId: Int = 1
    val allDefectLogs: LiveData<List<DefectLogEntity?>> by lazy { defectLogRepository.getAllDefectLogs(projectId) }

    //Manejadores del cronómetro
    private var isRunning: Boolean = false
    private var pauseOffset: Long = 0

    //Textos de los inputs del layout
    val txtDate: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val txtFixTime: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val txtDefectDescription: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val isEnabledBtnStart: MutableLiveData<Boolean> = MutableLiveData(true)
    val isEnabledBtnReg: MutableLiveData<Boolean> = MutableLiveData(false)

    // Acciones relacionadas con el cronómetro
    fun startChronometer(chronometer: Chronometer)
    {
        if (!isRunning)
        {
            chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
            chronometer.start()
            isRunning = true
        }
    }

    fun pauseChronometer(chronometer: Chronometer)
    {
        if (isRunning)
        {
            chronometer.stop()
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
            isRunning = false
        }
    }

    fun resetChronometer(chronometer: Chronometer)
    {
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        pauseOffset = 0
        isRunning = false

    }

    //TODO == Esta funcion se repite mucho, la puedo adecuar a *Common*

    fun setDate()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = LocalDateTime.now()
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm:ss")
            val final = formatter.format(localDateTime)
            final.let { txtDate.value = final }
        }
        else
        {
            val pattern = "d MMM yyyy HH:mm:ss"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val final = simpleDateFormat.format(Date())
            final.let { txtDate.value = final }
        }
        isEnabledBtnStart.value = false
        isEnabledBtnReg.value = true
    }

    fun insertDefectLog(defectLogEntity: DefectLogEntity) = viewModelScope.launch {
        defectLogRepository.insertDefectLog(defectLogEntity)
    }

}