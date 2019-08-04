package com.example.thelittlethings.database

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thelittlethings.database.Task
import java.sql.Date

@Entity(tableName = "day_table")
data class Day (

    @PrimaryKey(autoGenerate = true)
    var dayID: Long = 0L,

    @ColumnInfo(name = "date")
    var dayDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "tasks_complete")
    var tasksComplete: Int? = 0,

    @ColumnInfo(name = "tasks_complete_percent")
    var tasksCompletePercent: Double? = 0.0


)