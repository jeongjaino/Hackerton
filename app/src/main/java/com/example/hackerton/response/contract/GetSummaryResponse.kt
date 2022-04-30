package com.example.hackerton.response.contract

data class GetSummaryResponse (
    val workingTime: Long,
    val serviceTime: Long,
    val severancePay: Long
        )