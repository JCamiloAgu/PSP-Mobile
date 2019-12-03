package com.camilo.psp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.camilo.psp.data.dao.ProjectDao
import com.camilo.psp.data.entity.ProjectEntity


@Database(entities = [ProjectEntity::class], version = 1)
abstract class ProjectsRoomDatabase : RoomDatabase(){

    abstract fun projectDao(): ProjectDao

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