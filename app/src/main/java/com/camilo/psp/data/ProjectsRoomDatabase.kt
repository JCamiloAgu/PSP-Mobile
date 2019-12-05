package com.camilo.psp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.camilo.psp.data.dao.ProjectDao
import com.camilo.psp.data.dao.TimeLogDao
import com.camilo.psp.data.entity.ProjectEntity
import com.camilo.psp.data.entity.TimeLogEntity

@Database(entities = [ProjectEntity::class, TimeLogEntity::class], version = 1)
abstract class ProjectsRoomDatabase : RoomDatabase(){

    abstract fun projectDao(): ProjectDao
    abstract fun timeLogDao(): TimeLogDao

    companion object
    {
        @Volatile
        private var INSTANCE: ProjectsRoomDatabase? = null

        fun getDatabase(context: Context): ProjectsRoomDatabase
        {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectsRoomDatabase::class.java,
                    "projects_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}