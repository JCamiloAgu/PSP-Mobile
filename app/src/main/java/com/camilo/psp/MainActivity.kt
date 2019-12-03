package com.camilo.psp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.camilo.psp.adapters.ProjectsListAdapter
import com.camilo.psp.dialogs.NewProjectDialogFragment
import com.camilo.psp.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var adapter: ProjectsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        mainActivityViewModel.allProjects.observe(this, Observer { project ->
            project.let { adapter.setProjects(it) }
        })

        setUpEvents()
        setUpAdapter()
    }

    private fun setUpEvents()
    {
        fab.setOnClickListener {
            val newProjectDialog = NewProjectDialogFragment()
            newProjectDialog.show(supportFragmentManager, "newProjectDialog")
        }
    }

    private fun setUpAdapter() {
        val rcView = mainActivityRcView
        adapter = ProjectsListAdapter(this)
        rcView.adapter = adapter
        rcView.layoutManager = LinearLayoutManager(this)
    }

}
