package com.example.hackerton.service

import com.example.hackerton.reqeust.gps.GpsRequest
import com.example.hackerton.response.gps.GpsResponse
import com.example.hackerton.utils.Url.GET_GPS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GpsService {
    @POST(GET_GPS)
    suspend fun getGps(
    ): Response<GpsResponse>
}