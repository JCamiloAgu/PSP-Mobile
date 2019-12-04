package com.camilo.psp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.camilo.psp.common.setUpSpinner
import com.camilo.psp.databinding.ActivityTimeLogBinding
import com.camilo.psp.viewmodels.MainActivityViewModel
import com.camilo.psp.viewmodels.TimeLogViewModel
import kotlinx.android.synthetic.main.activity_time_log.*

class TimeLogActivity : AppCompatActivity() {

    private val timeLogActivityViewModel: TimeLogViewModel by lazy { ViewModelProviders.of(this)[TimeLogViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil
            .setContentView<ActivityTimeLogBinding>(this, R.layout.activity_time_log)
        binding.lifecycleOwner = this
        binding.viewmodel = timeLogActivityViewModel

        setUpSpinner(this, time_log_spinner, R.array.phases)

//        timeLogActivityViewModel = ViewModelProviders.of(this)[TimeLogViewModel::class.java]

//        setUpEvents()
//
//        setUpObservers()
    }

//    private fun setUpEvents() {
//        btn_start.setOnClickListener {
//            timeLogActivityViewModel.setActualTime(timeLogActivityViewModel.timeStart)
//            it.isEnabled = false
//            btn_stop.isEnabled = true
//        }
//
//        btn_stop.setOnClickListener {
//            timeLogActivityViewModel.setActualTime(timeLogActivityViewModel.timeStop)
//            it.isEnabled = false
//        }
//
//    }
//
//    private fun setUpObservers() {
//        timeLogActivityViewModel.timeStart.observe(this, Observer { txt_start.text = it })
//        timeLogActivityViewModel.timeStop.observe(this, Observer { txt_stop.text = it })
//    }

}
