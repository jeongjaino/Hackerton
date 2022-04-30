package com.example.hackerton.utils

import androidx.viewbinding.BuildConfig
import com.example.hackerton.service.GpsService
import com.example.hackerton.service.UserService
import com.example.hackerton.service.WorkService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUtil {

    val userService : UserService by lazy { getRetrofit().create(UserService::class.java)}
    val workService : WorkService by lazy{ getRetrofit().create(WorkService::class.java)}
    val gpsService: GpsService by lazy{ getRetrofit().create(GpsService::class.java)}

    private fun getRetrofit(): Retrofit{

        return Retrofit.Builder()
            .baseUrl(Url.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttpClient())
            .build()
    }

    private fun buildOkHttpClient(): OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG){
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else{
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            //.connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }
}
