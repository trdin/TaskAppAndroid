package com.example.notesapp

import android.app.Application
import com.example.tasks.Tasks

class MyApplication: Application() {
    lateinit var data: Tasks

    override fun onCreate() {
        super.onCreate()
        data = Tasks()
    }
}