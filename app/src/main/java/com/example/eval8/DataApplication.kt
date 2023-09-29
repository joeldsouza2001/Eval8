package com.example.eval8

import android.app.Application
import com.example.eval8.data.AppContainer
import com.example.eval8.data.DefaultAppContainer

class DataApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}