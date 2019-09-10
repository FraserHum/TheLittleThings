package com.example.thelittlethings.tasklist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thelittlethings.database.Day
import com.example.thelittlethings.database.DayDatabaseDao
import com.example.thelittlethings.database.Task
import com.example.thelittlethings.database.TaskDatabaseDao
import kotlinx.coroutines.*
import timber.log.Timber
import java.sql.Date
import javax.sql.CommonDataSource

class TaskListViewModel (dataSource: TaskDatabaseDao, dayDataSource: DayDatabaseDao,
                         appliaction: Application) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val currentTask = MutableLiveData<Task>()

    val database = dataSource
    val daysDatabase = dayDataSource



    val tasks = database.getAllTasks()
    var currentList = tasks.value?.toMutableList()

    private var currentDay = daysDatabase.getMostRecent().value



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



    fun checkDate(){
        uiScope.launch{
            Timber.i("checkDate")
            if(currentDay == null) currentDay = Day()
            if ( Date(currentDay!!.dayDate) != Date(System.currentTimeMillis())){
                onNewDay()
            }
        }
    }

    fun onNewDay(){
        Timber.i("onNewDay")
        if (database.getAllTasks().value != null) {
            val listSize = database.getAllTasks().value!!.size
            currentDay?.tasksComplete = listSize - tasks.value!!.size
            currentDay?.tasksCompletePercent = currentDay?.tasksComplete!! / listSize * 100.0

            daysDatabase.update(currentDay)

            val newDay = Day()
            newDay.dayDate = System.currentTimeMillis()

            daysDatabase.insert(newDay)
            currentDay = newDay
            currentList = tasks.value?.toMutableList()
        }


    }

    fun addNewestTaskToList(){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.getAddedTask()?.let {
                    Timber.i(it.taskName.toString())
                    currentList?.add(it)
                }
            }
        }
    }

    fun onTaskClicked(id : Long){
        currentList!!.forEach { if (it.taskID == id)
            currentList!!.remove(it)
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