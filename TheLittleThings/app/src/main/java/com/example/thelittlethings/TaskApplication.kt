package com.example.thelittlethings

import android.app.Application
import timber.log.Timber

class TaskApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

}