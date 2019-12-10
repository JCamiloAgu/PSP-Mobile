package com.camilo.psp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "defectLogs",
    foreignKeys = [ForeignKey(
        entity = ProjectEntity::class,
        parentColumns = ["id"],
        childColumns = ["projectId"],
        onDelete = ForeignKey.CASCADE
    )])

data class DefectLogEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val type: String,
    val phaseInjected: String,
    val phaseRemoved: String,
    val fixTime: String,
    val defectDescription: String,
    val projectId: Int
)