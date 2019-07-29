package com.example.thelittlethings.tasklist

import android.app.Application
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.thelittlethings.R
import com.example.thelittlethings.database.Task
import com.example.thelittlethings.database.TaskDatabaseDao
import kotlinx.coroutines.*
import timber.log.Timber

class TaskListViewModel(
    val database: TaskDatabaseDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val currentTask = MutableLiveData<Task>()

    val tasks = database.getAllTasks()

    val tasksFullList = database.getAllTasks()


    private val _navigateToTaskEntry = MutableLiveData<Boolean>()
    val navigateToTaskEntry : LiveData<Boolean>
        get() = _navigateToTaskEntry

    fun doneNavigating() {
        _navigateToTaskEntry.value = null
        Timber.i("doneNavigating()")
    }





    fun onNewTask() {
        uiScope.launch{
           _navigateToTaskEntry.value = true
            Timber.i("launched new task navigation")

        }
    }






    //TODO : fix this for editing tasks
//    private suspend fun getTaskFromDatabase(): Task? {
//        return withContext(Dispatchers.IO){
//          //  var task = database.get
//        }
//    }

    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }





    fun onClear(){
        uiScope.launch {
            clear()
            currentTask.value = null
            _showSnackbarEvent.value = true
        }
    }

    private suspend fun     clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



}