package com.example.hackerton.service

import com.example.hackerton.reqeust.career.HistoryRequest
import com.example.hackerton.reqeust.home.WorkEndRequest
import com.example.hackerton.response.calendar.CalendarResponse
import com.example.hackerton.response.career.HistoryResponse
import com.example.hackerton.response.contract.GetSummaryResponse
import com.example.hackerton.response.home.AcAmountResponse
import com.example.hackerton.response.home.PredictAmountResponse
import com.example.hackerton.response.home.WorkStartResponse
import com.example.hackerton.utils.Url.WORK_ACCRUE_AMOUNT
import com.example.hackerton.utils.Url.WORK_CALENDAR_DETAIL
import com.example.hackerton.utils.Url.WORK_END
import com.example.hackerton.utils.Url.WORK_HISTORY
import com.example.hackerton.utils.Url.WORK_PREDICT_AMOUNT
import com.example.hackerton.utils.Url.WORK_START
import com.example.hackerton.utils.Url.WORK_SUMMARY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WorkService {

    @POST(WORK_START)
    suspend fun workStart(
    ): Response<WorkStartResponse>

    @POST(WORK_END)
    suspend fun workEnd(
        @Body workEndRequest: WorkEndRequest
    ): Response<Void>

    @GET(WORK_PREDICT_AMOUNT)
    suspend fun getPredictAmount(
    ): Response<PredictAmountResponse>

    @GET(WORK_ACCRUE_AMOUNT)
    suspend fun getAcWorkAmount(
    ): Response<AcAmountResponse>

    @GET(WORK_HISTORY)
    suspend fun getWorkHistory(
    ): Response<MutableList<HistoryResponse>>

    @POST(WORK_HISTORY)
    suspend fun postWorkHistory(
        @Body requestWorkHistory: HistoryRequest
    ): Response<Void>

    @GET(WORK_SUMMARY)
    suspend fun getWorkSummary(
    ):Response<GetSummaryResponse>

    @GET(WORK_CALENDAR_DETAIL)
    suspend fun getCalendarDetail(@Query("date") date: String):Response<CalendarResponse>

}