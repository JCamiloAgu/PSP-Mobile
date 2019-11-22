package com.camilo.psp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = fab
        fab.setOnClickListener {
            val intent = Intent(this, FunctionsActivity::class.java)
            startActivity(intent)
        }
    }
}
