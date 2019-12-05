package com.camilo.psp.viewmodels

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimeLogViewModel : ViewModel() {

    //En estos campos se almacena la fecha en String para mostrarle al usuario
    val timeStartText: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val timeStopText: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //En estos campos se almacena la fecha en Date o DateTime para hacer el cálculo del deltaTime
    lateinit var timeStartDate: Any
    lateinit var timeStopDate: Any

    val deltaTime: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    var isEnabledBtnStart: MutableLiveData<Boolean> = MutableLiveData(true)
    //set(value) = isEnabledBtnStart.value(value)
    //Optimizando el cambio de estado de los botones
    var isEnabledBtnStop: MutableLiveData<Boolean> = MutableLiveData(false)

    fun setActualTime(toAffect: MutableLiveData<String>)
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

    fun setDeltaTime(timeStart: Any, timeStop: Any)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeStart as LocalDateTime
            timeStop as LocalDateTime
            deltaTime.value = (timeStop.minute - timeStart.minute).toString()
        }
        else {
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