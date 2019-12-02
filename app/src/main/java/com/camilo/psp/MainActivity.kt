package com.camilo.psp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.camilo.psp.adapters.ProjectsListAdapter
import com.camilo.psp.data.entity.ProjectEntity
import com.camilo.psp.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var adapter: ProjectsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        mainActivityViewModel.allProjects.observe(this, Observer { words ->
            words.let { adapter.setProjects(it) }
        })

        setUpEvents()
        setUpAdapter()
    }

    private fun setUpEvents()
    {
        fab.setOnClickListener {
            //val intent = Intent(this, FunctionsActivity::class.java)
            val project1 = ProjectEntity(0, "Tercero")
            mainActivityViewModel.insert(project1)
        }
    }

    private fun setUpAdapter() {
        val rcView = mainActivityRcView
        adapter = ProjectsListAdapter(this)
        rcView.adapter = adapter
        rcView.layoutManager = LinearLayoutManager(this)
    }
}
