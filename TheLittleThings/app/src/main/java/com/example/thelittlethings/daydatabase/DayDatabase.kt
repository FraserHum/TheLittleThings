package com.example.thelittlethings.daydatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import timber.log.Timber

@Database(entities = [Day::class], version = 1, exportSchema = false)
abstract class DayDatabase : RoomDatabase(){

    abstract val dayDatabaseDao: DayDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: DayDatabase? = null

        fun getInstance(context: Context): DayDatabase{

            synchronized(this){

                var instance = INSTANCE

                if(instance == null) {

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DayDatabase::class.java,
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