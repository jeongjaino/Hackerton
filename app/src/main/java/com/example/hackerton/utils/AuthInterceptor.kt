package com.example.hackerton.utils

import android.util.Log
import com.example.hackerton.App
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req =
            chain.request().newBuilder().addHeader("Authorization", "Bearer " + App.sharedPreferenceManager.token ?: "").build()
        return chain.proceed(req)
    }
}