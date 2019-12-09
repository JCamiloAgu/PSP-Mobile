package com.camilo.psp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.camilo.psp.common.setUpSpinner
import com.camilo.psp.data.entity.TimeLogEntity
import com.camilo.psp.databinding.ActivityTimeLogBinding
import com.camilo.psp.viewmodels.TimeLogViewModel


class TimeLogActivity : AppCompatActivity() {

    private val timeLogActivityViewModel: TimeLogViewModel by lazy { ViewModelProviders.of(this)[TimeLogViewModel::class.java] }
    private lateinit var binding: ActivityTimeLogBinding
    private var projectId: Int = 0
    private var phase: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_time_log)
        binding.viewmodel = timeLogActivityViewModel
        binding.lifecycleOwner = this

        projectId = intent.extras!!.get("ProjectId").toString().toInt()
        timeLogActivityViewModel.projectId = projectId


        setUpSpinner(this, binding.timeLogSpinner, R.array.phases)
        setUpListeners()
    }

    private fun setUpListeners()
    {

        timeLogActivityViewModel.timeLogInfo.observe(this, Observer {
            Log.d("ESTE", it?.toString() ?: "NADDA")
        })

        binding.btnReg.setOnClickListener {
//            if (isAllInputsCorrect()) {
                val phase = binding.timeLogSpinner.selectedItem.toString()
                val start = timeLogActivityViewModel.txtStart.value!!
                val interruption = binding.txtInterruption.text.toString().toInt()
                val stop = timeLogActivityViewModel.txtStop.value!!
                val delta = timeLogActivityViewModel.txtDelta.value!!
                val comments = binding.txtComments.text.toString()

                val timeLogEntity =
                    TimeLogEntity(0, phase, start, interruption, stop, delta, comments, projectId)

                timeLogActivityViewModel.insertTimeLog(timeLogEntity)
//            }
        }

        binding.timeLogSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                pos: Int,
                id: Long
            ) {
                phase = parent.getItemAtPosition(pos).toString()
                timeLogActivityViewModel.strategySelected(phase)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        
    }

    private fun isAllInputsCorrect(): Boolean
    {
        if(binding.txtInterruption.text.isNullOrBlank()) {
            binding.txtInterruption.error = "Este campo no puede estar vacío"
            return false
        }

        return true

    }


}
