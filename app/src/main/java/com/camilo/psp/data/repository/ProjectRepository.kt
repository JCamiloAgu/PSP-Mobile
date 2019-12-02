package com.camilo.psp.data.repository

import androidx.lifecycle.LiveData
import com.camilo.psp.data.dao.ProjectDao
import com.camilo.psp.data.entity.ProjectEntity

class ProjectRepository(private val projectDao: ProjectDao)
{
    //Puede que esta linea de errores. por el data class, quizás tenga que hacer 2
    val allProjects: LiveData<List<ProjectEntity>> = projectDao.getAllProjects()

    suspend fun insertProject (projectEntity: ProjectEntity){
        projectDao.insertProject(projectEntity)
    }
}