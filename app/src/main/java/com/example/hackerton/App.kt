package com.example.hackerton

import android.app.Application
import android.content.Context
import com.example.hackerton.utils.SharedPreferenceManager


class App: Application() {

    companion object{
        lateinit var sharedPreferenceManager: SharedPreferenceManager
        private var instance: App? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
    init {
        instance = this
    }

    override fun onCreate(){
        super.onCreate()
        sharedPreferenceManager = SharedPreferenceManager(applicationContext)
    }

}