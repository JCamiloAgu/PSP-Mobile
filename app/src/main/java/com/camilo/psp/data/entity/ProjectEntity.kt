package com.camilo.psp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "projects")
data class ProjectEntity(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val nameProject: String
)