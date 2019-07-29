package com.example.thelittlethings.daydatabase

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
    var dayDate: Date = Date(System.currentTimeMillis()),

    @ColumnInfo(name = "unfinished_list")
    var taskNumber: List<Task>? = null,

    @ColumnInfo(name = "full_list")
    var fullList: List<Task>? = null


)