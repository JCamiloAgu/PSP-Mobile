package com.camilo.psp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.camilo.psp.data.ProjectsRoomDatabase
import com.camilo.psp.data.entity.ProjectEntity
import com.camilo.psp.data.repository.ProjectRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val projectsDao = ProjectsRoomDatabase.getDatabase(application).projectDao()
    private val repository: ProjectRepository = ProjectRepository(projectsDao)
    val allProjects: LiveData<List<ProjectEntity>> = repository.allProjects

    fun insert(projectEntity: ProjectEntity) = viewModelScope.launch {
        repository.insertProject(projectEntity)
    }


}