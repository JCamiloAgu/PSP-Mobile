package com.camilo.psp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


//Debo probar esto
@Entity(
    tableName = "timeLogs",
    foreignKeys = [ForeignKey(
        entity = ProjectEntity::class,
        parentColumns = ["id"],
        childColumns = ["projectId"],
        onDelete = ForeignKey.CASCADE
        )])

data class TimeLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val phase: String,
    val start: String,
    val interruption: Int,
    val stop: String,
    val delta: String,
    val comments: String?,
    val projectId: Int
)