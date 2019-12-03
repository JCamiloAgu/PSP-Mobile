package com.camilo.psp.dialogs

import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

import com.camilo.psp.R
import com.camilo.psp.data.entity.ProjectEntity
import com.camilo.psp.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.new_project_dialog_fragment.view.*

class NewProjectDialogFragment : DialogFragment() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.new_project_dialog_fragment, null)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.new_project_dialog_title)
            builder.setMessage(R.string.new_project_dialog_message)
                .setPositiveButton((R.string.new_project_dialog_positive_btn)
                ) { dialog, _->
                    val nameProject = view.edt_txt_name_project.text.toString()
                    if(nameProject.isNotEmpty())
                    {
                        val newProject = ProjectEntity(0, nameProject)
                        viewModel.insert(newProject)
                        dialog.dismiss()
                    }else
                        Toast.makeText(activity, R.string.new_project_dialog_positive_btn_error, Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(R.string.new_project_dialog_negative_btn
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            builder.setView(view)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
