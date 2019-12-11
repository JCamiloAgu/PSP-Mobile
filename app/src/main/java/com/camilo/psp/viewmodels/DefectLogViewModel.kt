package com.camilo.psp.viewmodels

import android.app.Application
import android.os.Build
import android.os.SystemClock
import android.widget.Chronometer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DefectLogViewModel(application: Application) : AndroidViewModel(application)
{
    //TODO = Instanciar el repositorio


    var isRunning: Boolean = false
    private var pauseoffset: Long = 0

    val txtDate: MutableLiveData<String> = MutableLiveData()
    val txtFixTime: MutableLiveData<String> = MutableLiveData()
    val txtDefectDescription: MutableLiveData<String> = MutableLiveData()

    val isEnabledBtnStart: MutableLiveData<Boolean> = MutableLiveData(true)


    // Acciones relacionadas con el cronómetro
    fun startChronometer(chronometer: Chronometer)
    {
        if (!isRunning)
        {
            chronometer.base = SystemClock.elapsedRealtime() - pauseoffset
            chronometer.start()
            isRunning = true
        }
    }

    fun pauseChronometer(chronometer: Chronometer)
    {
        if (isRunning)
        {
            chronometer.stop()
            pauseoffset = SystemClock.elapsedRealtime() - chronometer.base
            isRunning = false
        }
    }

    fun resetChronometer(chronometer: Chronometer)
    {
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        pauseoffset = 0
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
    }

}