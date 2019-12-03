package com.camilo.psp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.camilo.psp.common.setUpSpinner
import kotlinx.android.synthetic.main.activity_time_log.*

class TimeLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_log)

        setUpSpinner(this, time_log_spinner, R.array.phases)

    }

}
