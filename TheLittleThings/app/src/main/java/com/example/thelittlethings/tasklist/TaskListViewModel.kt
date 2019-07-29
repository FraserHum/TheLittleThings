package com.example.thelittlethings.tasklist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thelittlethings.database.Task
import com.example.thelittlethings.database.TaskDatabaseDao
import kotlinx.coroutines.*
import timber.log.Timber
import java.sql.Date

class TaskListViewModel(
    val database: TaskDatabaseDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val currentTask = MutableLiveData<Task>()

    val tasks = database.getAllTasks()  //TODO: encapsulate properly




    val listDate : Date = Date(System.currentTimeMillis()) // TODO: set this to the lists date


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

    fun currentDayList() : List<Task> {
        //TODO: return the correct list for the day
        return tasks
    }

    fun checkDate(){
        uiScope.launch{
            if ( listDate != Date(System.currentTimeMillis())){
                onNewDay()
            }

        }

    }

    fun onNewDay(){}






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