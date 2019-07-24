package com.example.thelittlethings.taskentry

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thelittlethings.database.TaskDatabaseDao
import com.example.thelittlethings.tasklist.TaskListViewModel

class TaskEntryViewModelFactory (
    private val dataSource: TaskDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskEntryViewModel::class.java)) {
                return TaskEntryViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

}