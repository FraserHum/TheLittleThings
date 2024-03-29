package com.example.thelittlethings.taskentry

import android.app.ActivityManager
import android.app.Application
import android.widget.EditText
import androidx.databinding.Bindable
import androidx.databinding.InverseMethod
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thelittlethings.R
import com.example.thelittlethings.database.Task
import com.example.thelittlethings.database.TaskDatabaseDao
import kotlinx.android.synthetic.main.fragment_task_entry.view.*
import kotlinx.coroutines.*
import timber.log.Timber

class TaskEntryViewModel( val database: TaskDatabaseDao) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)




    private val _navigateToTaskList = MutableLiveData<Boolean>()
    val navigateToTaskList : LiveData<Boolean>
        get() = _navigateToTaskList

    private val _createNewTask = MutableLiveData<Boolean>()
    val createNewTask : LiveData<Boolean>
        get() = _createNewTask

    fun doneNavigating() {
        _navigateToTaskList.value = null
        Timber.i("doneNavigating()")
    }







    fun onCreateNewTask() {
        _createNewTask.value = true
        Timber.i("onCreateNewTask")

    }



    fun createNewTask(taskName: String, taskDescription: String){
        uiScope.launch {
            val newTask = Task()
            newTask.taskName = taskName.capitalize()
            newTask.taskDescription = taskDescription
            newTask.taskNumber = 0
            Timber.i("new task made")
            insert(newTask)
            Timber.i("new task inserted")
        }

        _createNewTask.value = null

        _navigateToTaskList.value = true


    }

    private suspend fun insert(task: Task){
        withContext(Dispatchers.IO){
            database.insert(task)
        }
    }
}