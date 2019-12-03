package com.camilo.psp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_functions.*

class FunctionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_functions)

        val btnTimeLog = btn_time_log
        val btnDefectLog = btn_defect_log

        textViewTitle.text = intent.extras?.get("ProjectName").toString()

        btnTimeLog.setOnClickListener {
            val intent = Intent(this, TimeLogActivity::class.java)
            startActivity(intent)
        }

        btnDefectLog.setOnClickListener {
            val intent = Intent(this, DefectLogActivity::class.java)
            startActivity(intent)
        }
    }
}
