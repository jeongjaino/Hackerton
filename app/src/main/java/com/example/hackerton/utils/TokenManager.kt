package com.example.hackerton.utils

import android.content.Context


class TokenManager(context: Context) {
    companion object{
        const val PREF = "Preference"
    }
    private val preference = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    var token: String?
        get() = preference.getString("token", null)
        set(value) = preference.edit().putString("token", value).apply()
}