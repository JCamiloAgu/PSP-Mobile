package com.camilo.psp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

//         val porjectId = intent.extras!!.get("ProjectId").toString().toInt()
//        timeLogActivityViewModel.projectId = porjectId

        setUpSpinner(this, time_log_spinner, R.array.phases)

    }



}
