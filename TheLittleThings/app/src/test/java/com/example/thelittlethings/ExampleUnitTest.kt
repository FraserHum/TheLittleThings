package com.example.thelittlethings

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.thelittlethings.database.Task
import com.example.thelittlethings.database.TaskDatabase
import com.example.thelittlethings.database.TaskDatabaseDao
import org.junit.After
import org.junit.Test



import org.junit.Assert.*
import org.junit.Before

import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class SleepDatabaseTest {

    private lateinit var taskDao: TaskDatabaseDao
    private lateinit var db: TaskDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        taskDao = db.taskDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val task = Task()
        taskDao.insert(task)
        val tonight = taskDao.get(task.taskID)
        assertEquals(tonight?.taskNumber, -1)
    }
}