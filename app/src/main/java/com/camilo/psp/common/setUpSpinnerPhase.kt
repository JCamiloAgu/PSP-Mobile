package com.camilo.psp.common

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

fun setUpSpinner(context: Context, spinner: Spinner, phase: Int){

    ArrayAdapter.createFromResource(
        context,
        phase,
        android.R.layout.simple_spinner_item
    ).also { adapter ->
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}