package com.example.hackerton.service

import com.example.hackerton.reqeust.SignInInfo
import com.example.hackerton.reqeust.SignUpInfo
import com.example.hackerton.response.SignInResponse
import com.example.hackerton.response.SignUpResponse
import com.example.hackerton.utils.Url.SIGN_IN
import com.example.hackerton.utils.Url.SIGN_UP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST(SIGN_IN)
    suspend fun userSignIn(
        @Body signInInfo: SignInInfo
    ):Response<SignInResponse>

    @POST(SIGN_UP)
    suspend fun userSignUp(
        @Body signUpInfo: SignUpInfo
    ):Response<SignUpResponse>

}