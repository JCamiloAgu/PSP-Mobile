package com.camilo.psp.viewmodels

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimeLogViewModel : ViewModel() {

    val timeStart: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val timeStop: MutableLiveData<String> by lazy { MutableLiveData<String>() }

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
        }
        else
        {
            val pattern = "d MMM yyyy HH:mm:ss"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val final = simpleDateFormat.format(Date())
            final.let { toAffect.value = final }
        }
    }




}