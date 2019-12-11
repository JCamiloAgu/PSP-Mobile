package com.camilo.psp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.camilo.psp.common.setUpSpinner
import com.camilo.psp.data.entity.DefectLogEntity
import com.camilo.psp.databinding.ActivityDefectLogBinding
import com.camilo.psp.viewmodels.DefectLogViewModel
import kotlinx.android.synthetic.main.activity_defect_log.*

class DefectLogActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer

    private lateinit var binding: ActivityDefectLogBinding
    private val defectLogViewModel: DefectLogViewModel by lazy { ViewModelProviders.of(this)[DefectLogViewModel::class.java] }

    private var projectId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_defect_log)
        binding.viewmodel =  defectLogViewModel
        binding.lifecycleOwner = this

        projectId = intent.extras!!.get("ProjectId").toString().toInt()
        defectLogViewModel.projectId = projectId

        chronometer = binding.myChronometer
        chronometer.base = SystemClock.elapsedRealtime()

        setUpSpinners()
        setUpEvents()
        setUpObservers()
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
            // TODO = Debo preguntar cual es el cálculo que hay que hacer.
            defectLogViewModel.txtFixTime.value = chronometer.text.toString()
        }

        chronometer.setOnChronometerTickListener {
            when {
                chronometer.text == "00:00" -> binding.circle.progress += 0
                binding.circle.progress < 59 -> binding.circle.progress += 1
                else -> binding.circle.progress = 0
            }
        }

        binding.btnReg.setOnClickListener {
            if(doValidations()) {
                val date = defectLogViewModel.txtDate.value!!
                val type = binding.typeSpinner.selectedItem.toString()
                val phaseInjected = binding.phaseInjectedSpinner.selectedItem.toString()
                val phaseRemoved = binding.phaseRemovedSpinner.selectedItem.toString()
                val fixTime = defectLogViewModel.txtFixTime.value!!
                val defectDescription = binding.edTextDefectDescription.text.toString()

                val defectLogEntity = DefectLogEntity(
                    0,
                    date,
                    type,
                    phaseInjected,
                    phaseRemoved,
                    fixTime,
                    defectDescription,
                    projectId
                )
                defectLogViewModel.insertDefectLog(defectLogEntity)
            }

        }
    }

    private fun setUpSpinners()
    {
        setUpSpinner(this, type_spinner, R.array.type)
        setUpSpinner(this, phase_injected_spinner, R.array.phases)
        setUpSpinner(this, phase_removed_spinner, R.array.phases)
    }

    private fun doValidations(): Boolean
    {
        if (binding.edTextDefectDescription.text.isNullOrBlank()){
            binding.edTextDefectDescription.error = "Este campo no puede estar vacio"
            return false
        }

        if(defectLogViewModel.txtFixTime.value.isNullOrBlank()) {
            binding.textViewErrors.text = "Debe utilizar el cronómetro para calcular el FixTime"
            return false
        }
        else
            binding.textViewErrors.text = ""

        return true
    }

    private fun setUpObservers() {
        defectLogViewModel.allDefectLogs.observe(this, Observer {
            Log.d("ALL DEFECTS", it.toString())
        })
    }

}
