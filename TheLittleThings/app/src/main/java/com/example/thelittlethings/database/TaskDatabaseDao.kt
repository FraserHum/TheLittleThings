package com.example.thelittlethings.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDatabaseDao {
    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Query( "SELECT * from task_table WHERE taskID = :key")
    fun get(key: Long): Task

    @Query ("DELETE FROM task_table")
    fun clear()

    @Query ("SELECT * from task_table ORDER BY task_number DESC")
    fun getAllTasks(): LiveData<List<Task>>
}