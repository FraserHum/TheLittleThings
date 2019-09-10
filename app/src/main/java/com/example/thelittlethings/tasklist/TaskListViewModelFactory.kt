package com.example.thelittlethings.tasklist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thelittlethings.database.DayDatabaseDao


import com.example.thelittlethings.database.TaskDatabaseDao

class TaskListViewModelFactory(
    private val dataSource: TaskDatabaseDao,
    private val daysDataSource: DayDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                return TaskListViewModel(dataSource, daysDataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}