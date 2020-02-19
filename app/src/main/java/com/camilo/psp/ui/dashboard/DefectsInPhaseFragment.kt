package com.camilo.psp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.camilo.psp.data.entity.DefectLogEntity
import com.camilo.psp.databinding.FragmentDefectsInPhaseBinding
import com.camilo.psp.viewmodels.DefectsInPhaseViewModel

class DefectsInPhaseFragment : Fragment() {

    private val defectsInPhaseViewModel by viewModels<DefectsInPhaseViewModel>()
    lateinit var binding: FragmentDefectsInPhaseBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDefectsInPhaseBinding.inflate(inflater, container, false)
        binding.viewmodel = defectsInPhaseViewModel
        binding.lifecycleOwner = this


        val defectsArg by navArgs<DefectsInPhaseFragmentArgs>()
        val isDefectsInjected: Boolean = defectsArg.isDefectsInjected

        if (!arguments?.getString("ProjectId").isNullOrEmpty()) {
            defectsInPhaseViewModel.projectId = arguments?.getString("ProjectId")!!.toInt()
        }

        val defectLogList: LiveData<List<DefectLogEntity?>>? = defectsInPhaseViewModel.defectLogData
        defectLogList?.observe(this, Observer {
            Log.d("DEFECT LIST", it.toString())
            if (!it.isNullOrEmpty()) {
                defectsInPhaseViewModel.calculateNumberOfDefectsInPhase(it, isDefectsInjected)
                defectsInPhaseViewModel.calculatePercentDefectsInPhase()
            }
        })

        val root = binding.root

        return root
    }
}