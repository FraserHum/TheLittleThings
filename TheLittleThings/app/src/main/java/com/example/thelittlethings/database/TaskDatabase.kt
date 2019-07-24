package com.example.thelittlethings.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import timber.log.Timber
import java.security.AccessControlContext

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase(){

    abstract val taskDatabaseDao: TaskDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase{

            synchronized(this){

                var instance = INSTANCE

                if(instance == null) {
                    Timber.i("instance == null")
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "task_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    Timber.i("database built")
                    INSTANCE = instance
                }
                Timber.i("database returned")
                return instance
            }
        }

    }
}