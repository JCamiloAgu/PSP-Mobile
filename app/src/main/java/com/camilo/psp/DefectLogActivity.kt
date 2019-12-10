package com.camilo.psp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.Toast
import com.camilo.psp.common.setUpSpinner
import kotlinx.android.synthetic.main.activity_defect_log.*

class DefectLogActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer
    private var isRunning: Boolean = false
    private var pauseoffset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defect_log)

        chronometer = myChronometer

        setUpSpinner(this, type_spinner, R.array.type)
        setUpSpinner(this, phase_injected_spinner, R.array.phases)
        setUpSpinner(this, phase_removed_spinner, R.array.phases)

        setUpEvents()

        chronometer.base = SystemClock.elapsedRealtime()

        chronometer.setOnChronometerTickListener {
            Log.d("Chronometer", it.text.toString())
            if((SystemClock.elapsedRealtime() - chronometer.base) >= 600000)
            {
                it.base = SystemClock.elapsedRealtime()
                Toast.makeText(this, "Bing", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun setUpEvents() {
        imageButtonStart.setOnClickListener{
            startChronometer()
        }

        imageButtonReset.setOnClickListener {
            resetChronometer()
        }

        imageButtonStop.setOnClickListener {
            pauseChronometer()
        }
    }

    private fun startChronometer()
    {
        if (!isRunning)
        {
            chronometer.base = SystemClock.elapsedRealtime() - pauseoffset
            chronometer.start()
            isRunning = true
        }
    }

    private fun pauseChronometer()
    {
        if (isRunning)
        {
            chronometer.stop()
            pauseoffset = SystemClock.elapsedRealtime() - chronometer.base
            isRunning = false
        }
    }

    private fun resetChronometer()
    {
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        pauseoffset = 0
        isRunning = false

    }
}
