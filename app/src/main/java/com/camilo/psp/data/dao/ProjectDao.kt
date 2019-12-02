package com.camilo.psp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camilo.psp.data.entity.ProjectEntity

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects")
    fun getAllProjects(): LiveData<List<ProjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(projectEntity: ProjectEntity)
}