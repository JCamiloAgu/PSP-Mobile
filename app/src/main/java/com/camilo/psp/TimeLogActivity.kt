package com.camilo.psp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.camilo.psp.common.setUpSpinner
import com.camilo.psp.data.entity.TimeLogEntity
import com.camilo.psp.databinding.ActivityTimeLogBinding
import com.camilo.psp.viewmodels.TimeLogViewModel


class TimeLogActivity : AppCompatActivity() {

    private val timeLogActivityViewModel: TimeLogViewModel by lazy { ViewModelProviders.of(this)[TimeLogViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTimeLogBinding = DataBindingUtil.setContentView(this, R.layout.activity_time_log)
        binding.viewmodel = timeLogActivityViewModel
        binding.lifecycleOwner = this
        val projectId = intent.extras!!.get("ProjectId").toString().toInt()
        timeLogActivityViewModel.projectId = projectId

        binding.timeLogSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                pos: Int,
                id: Long
            ) {
                timeLogActivityViewModel.phase = parent.getItemAtPosition(pos).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btnPrueba.setOnClickListener {
            Log.d("INTERPRETAR", timeLogActivityViewModel.timeLogInfo.value?.size.toString() )
        }


        binding.btnReg.setOnClickListener {
            val phase = binding.timeLogSpinner.selectedItem.toString()
            val start = timeLogActivityViewModel.timeStartText.value!!
            val interruption = binding.txtInterruption.text.toString().toInt()
            val stop = timeLogActivityViewModel.timeStopText.value!!
            val delta = timeLogActivityViewModel.deltaTime.value!!
            val comments = binding.txtComments.text.toString()

            val timeLogEntity = TimeLogEntity(0, phase, start, interruption, stop, delta, comments, projectId)

            timeLogActivityViewModel.insertTimeLog(timeLogEntity)
        }
        setUpSpinner(this, binding.timeLogSpinner, R.array.phases)


        timeLogActivityViewModel.timeLogInfo.observe(this, Observer {
            it?.let {
//                binding.txtStart.text = it.start
//                (binding.txtInterruption as TextView).text = it.interruption.toString()
//                binding.txtStop.text = it.stop
//                binding.txtDelta.text = it.delta
//                (binding.txtComments as TextView).text = it.comments

//                timeLogActivityViewModel.buttonsJ()

                var l = timeLogActivityViewModel.timeLogInfo.value.toString()
                Log.d("Buscar", l)
            }
        })



    }



}
