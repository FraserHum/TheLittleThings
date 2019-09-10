package com.example.thelittlethings.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task (

    @PrimaryKey(autoGenerate = true)
    var taskID: Long = 0L,

    @ColumnInfo(name = "task_name")
    var taskName: String? = "",

    @ColumnInfo(name = "task_description")
    var taskDescription: String? = "",

    @ColumnInfo(name = "task_number") //TODO: Implement Item ordering
    var taskNumber: Int = -1
)
