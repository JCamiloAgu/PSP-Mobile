package com.camilo.psp.viewmodels

import android.app.Application
import android.os.SystemClock
import android.widget.Chronometer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class DefectLogViewModel(application: Application) : AndroidViewModel(application)
{
    //TODO = Instanciar el repositorio


    var isRunning: Boolean = false
    private var pauseoffset: Long = 0
    var base: Long = SystemClock.elapsedRealtime()


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
            base = chronometer.base
            chronometer.start()
            isRunning = true
        }
    }

    fun pauseChronometer(chronometer: Chronometer)
    {
        if (isRunning)
        {
            chronometer.stop()
            pauseoffset = SystemClock.elapsedRealtime() - base
            isRunning = false
        }
    }

    fun resetChronometer(chronometer: Chronometer)
    {
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        base = chronometer.base
        pauseoffset = 0
        isRunning = false

    }

}