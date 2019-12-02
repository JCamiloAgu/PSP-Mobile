package com.camilo.psp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.camilo.psp.data.dao.ProjectDao
import com.camilo.psp.data.entity.ProjectEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ProjectEntity::class], version = 1)
abstract class ProjectsRoomDatabase : RoomDatabase(){

    abstract fun projectDao(): ProjectDao

//    private class ProjectsDatabaseCallback(private val scope: CoroutineScope):RoomDatabase.Callback()
//    {
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    populateDatabase(database.projectDao())
//
//                }
//            }
//        }
//
//        suspend fun populateDatabase(projectDao: ProjectDao) {
//            var project = ProjectEntity(null,"Primero")
//            projectDao.insertProject(project)
//            project = ProjectEntity(null, "Segundo")
//            projectDao.insertProject(project)
//        }
//    }

    companion object
    {
        @Volatile
        private var INSTANCE: ProjectsRoomDatabase? = null

//        fun getDatabase(context: Context, scope: CoroutineScope): ProjectsRoomDatabase
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
//                    .addCallback(ProjectsDatabaseCallback(scope))
                INSTANCE = instance
                return instance
            }

        }
    }
}