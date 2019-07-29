package com.example.thelittlethings.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DayDatabaseDao {
    @Insert
    fun insert(day: Day)

    @Update
    fun update(day: Day)

    @Query( "SELECT * from day_table WHERE dayID = :key")
    fun get(key: Long): Day

    @Query("DELETE FROM day_table")
    fun clear()

    @Query("SELECT * from day_table ORDER BY date DESC LIMIT 1")
    fun getMostRecent(): Day
}