package com.camilo.psp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.camilo.psp.common.setUpSpinner
import kotlinx.android.synthetic.main.activity_defect_log.*

class DefectLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defect_log)

        setUpSpinner(this, type_spinner, R.array.type)
        setUpSpinner(this, phase_injected_spinner, R.array.phases)
        setUpSpinner(this, phase_removed_spinner, R.array.phases)
    }
}
