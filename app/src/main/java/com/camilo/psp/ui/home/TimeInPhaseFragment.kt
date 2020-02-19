package com.camilo.psp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.camilo.psp.data.entity.TimeLogEntity
import com.camilo.psp.databinding.FragmentTimeInPhaseBinding
import com.camilo.psp.viewmodels.TimeInPhaseViewModel

class TimeInPhaseFragment : Fragment() {

    private val timeInPhaseViewModel by viewModels<TimeInPhaseViewModel>()
    lateinit var binding: FragmentTimeInPhaseBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimeInPhaseBinding.inflate(inflater, container, false)
        binding.viewmodel = timeInPhaseViewModel
        binding.lifecycleOwner = this


        if (!arguments?.getString("ProjectId").isNullOrEmpty())
            timeInPhaseViewModel.projectId = arguments?.getString("ProjectId")!!.toInt()

        val root = binding.root

        val timeLogData: LiveData<List<TimeLogEntity?>>? = timeInPhaseViewModel.timeLogData
        timeLogData?.observe(this, Observer {
            Log.d("DATA FROM OBSERVER", it.toString())

            if (!it.isNullOrEmpty()) {
                it.forEach { entity ->
                    when (entity!!.phase) {
                        "PLAN" -> setValuesToView(
                            timeInPhaseViewModel.txtTimePhasePlan,
                            timeInPhaseViewModel.txtPercentTimeInPlan,
                            entity.delta,
                            it
                        )
                        "DDL" -> setValuesToView(
                            timeInPhaseViewModel.txtTimePhaseDdl,
                            timeInPhaseViewModel.txtPercentTimeInDdl,
                            entity.delta,
                            it
                        )
                        "CODE" -> setValuesToView(
                            timeInPhaseViewModel.txtTimePhaseCode,
                            timeInPhaseViewModel.txtPercentTimeInCode,
                            entity.delta,
                            it
                        )
                        "COMPILE" -> setValuesToView(
                            timeInPhaseViewModel.txtTimePhaseCompile,
                            timeInPhaseViewModel.txtPercentTimeInCompile,
                            entity.delta,
                            it
                        )
                        "UT" -> setValuesToView(
                            timeInPhaseViewModel.txtTimePhaseUt,
                            timeInPhaseViewModel.txtPercentTimeInUt,
                            entity.delta,
                            it
                        )
                        "PM" -> setValuesToView(
                            timeInPhaseViewModel.txtTimePhasePm,
                            timeInPhaseViewModel.txtPercentTimeInPm,
                            entity.delta,
                            it
                        )
                    }
                }
            }
        })

        return root
    }

    private fun setValuesToView(
        txtTimePhase: MutableLiveData<String>,
        txtPercentTime: MutableLiveData<String>,
        delta: String,
        timeLogData: List<TimeLogEntity?>?
    ) {
        txtTimePhase.value = "$delta min"
        txtPercentTime.value = timeInPhaseViewModel.getTimePercent(timeLogData!!, delta.toInt())
    }

}