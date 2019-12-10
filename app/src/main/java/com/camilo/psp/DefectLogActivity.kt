package com.camilo.psp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Chronometer
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.camilo.psp.common.setUpSpinner
import com.camilo.psp.databinding.ActivityDefectLogBinding
import com.camilo.psp.viewmodels.DefectLogViewModel
import kotlinx.android.synthetic.main.activity_defect_log.*

class DefectLogActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer

    private lateinit var binding: ActivityDefectLogBinding
    private val defectLogViewModel: DefectLogViewModel by lazy { ViewModelProviders.of(this)[DefectLogViewModel::class.java] }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_defect_log)
        binding.viewmodel =  defectLogViewModel
        binding.lifecycleOwner = this

        chronometer = binding.myChronometer


        chronometer.base = defectLogViewModel.base

        if (defectLogViewModel.isRunning) {
            defectLogViewModel.isRunning = false
            defectLogViewModel.startChronometer(chronometer)
        }

        setUpSpinners()
        setUpEvents()
    }

    private fun setUpEvents()
    {
        imageButtonStart.setOnClickListener{
            defectLogViewModel.startChronometer(chronometer)
        }

        imageButtonReset.setOnClickListener {
            defectLogViewModel.resetChronometer(chronometer)
            binding.circle.progress = 0
        }

        imageButtonStop.setOnClickListener {
            defectLogViewModel.pauseChronometer(chronometer)
        }

        chronometer.setOnChronometerTickListener {
            if (it.text[3].toString().toInt() == 5 && it.text[4].toString().toInt() == 9)
                binding.circle.progress = 0
            else
                binding.circle.progress += 1
        }
    }

    private fun setUpSpinners()
    {
        setUpSpinner(this, type_spinner, R.array.type)
        setUpSpinner(this, phase_injected_spinner, R.array.phases)
        setUpSpinner(this, phase_removed_spinner, R.array.phases)
    }

}
