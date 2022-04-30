package com.example.hackerton.response.career

data class HistoryResponse (
    val isValid: Long,
    val officeName: String,
    val role: String,
    val attributeRate: Long,
    val troubleshootingRate: Long,
    val startDate: String,
    val endDate: String
        )