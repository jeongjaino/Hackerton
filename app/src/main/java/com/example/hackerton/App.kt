package com.example.hackerton

import android.app.Application
import com.example.hackerton.utils.TokenManager


class App: Application() {
    companion object{
        lateinit var tokenManager: TokenManager
    }
    override fun onCreate(){
        super.onCreate()
        tokenManager = TokenManager(applicationContext)
    }
}