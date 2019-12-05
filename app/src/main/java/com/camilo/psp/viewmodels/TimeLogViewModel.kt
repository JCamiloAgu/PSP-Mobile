package com.camilo.psp.viewmodels

import android.app.Application
import android.os.Build
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.camilo.psp.data.ProjectsRoomDatabase
import com.camilo.psp.data.ProjectsRoomDatabase_Impl
import com.camilo.psp.data.entity.TimeLogEntity
import com.camilo.psp.data.repository.TimeLogRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimeLogViewModel(application: Application) : AndroidViewModel(application) {

    // Repositorio
    private val timeLogRepository: TimeLogRepository
    var timeLogInfo: MutableLiveData<TimeLogEntity?>? = null

    init {
        val timeLogDao = ProjectsRoomDatabase.getDatabase(application).timeLogDao()
        timeLogRepository = TimeLogRepository(timeLogDao)
    }

    //En estos campos se almacena la fecha en String para mostrarle al usuario
    val timeStartText: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val timeStopText: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //En estos campos se almacena la fecha en Date o DateTime para hacer el cálculo del deltaTime
    lateinit var timeStartDate: Any
    lateinit var timeStopDate: Any

    val deltaTime: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    // Estas variables establecen la visibilidad de los botones de Start y de Stop
    var isEnabledBtnStart: MutableLiveData<Boolean> = MutableLiveData(true)
    var isEnabledBtnStop: MutableLiveData<Boolean> = MutableLiveData(false)

    var projectId = -1

    private fun setActualTime(toAffect: MutableLiveData<String>)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = LocalDateTime.now()
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm:ss")
            val final = formatter.format(localDateTime)
            final.let { toAffect.value = final }
            if (toAffect == timeStartText)
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
            if (toAffect == timeStartText)
                timeStartDate = Date()
            else
                timeStopDate = Date()
        }
    }

    private fun setDeltaTime(timeStart: Any, timeStop: Any)
    {
        if (!isEnabledBtnStart.value!! && !isEnabledBtnStop.value!!) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                timeStart as LocalDateTime
                timeStop as LocalDateTime
                deltaTime.value = (timeStop.minute - timeStart.minute).toString()
            } else {
                timeStart as Date
                timeStop as Date

                val timeDiff: Long = timeStop.time - timeStart.time

                val segMilli: Long = 1000
                val minutesMilli = segMilli * 60
                val timeDiffMinutes = timeDiff / minutesMilli
                deltaTime.value = timeDiffMinutes.toString()
            }
        }
    }

    fun btnsClick(toAffect: MutableLiveData<String>) {
        if (toAffect == timeStartText) {
            setActualTime(toAffect)
            isEnabledBtnStart.value = false
            isEnabledBtnStop.value = true

        }
        else {
            setActualTime(toAffect)
            isEnabledBtnStop.value = false
            setDeltaTime(timeStartDate, timeStopDate)
        }



    }

}