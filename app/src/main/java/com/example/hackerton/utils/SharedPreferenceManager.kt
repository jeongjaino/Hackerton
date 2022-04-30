package com.example.hackerton.utils

import android.content.Context


class SharedPreferenceManager(context: Context) {
    companion object{
        const val PREF = "Preference"
    }
    private val preference = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    var token: String?
        get() = preference.getString("token", null)
        set(value) = preference.edit().putString("token", value).apply()

    var time: Long?
        get() = preference.getLong("time", 0)
        set(currentTime) = preference.edit().putLong("time", currentTime!!).apply()

    var isWorkOn: Boolean?
        get() = preference.getBoolean("isWorkOn", true)
        set(isWorkOn) = preference.edit().putBoolean("isWorkOn", isWorkOn!!).apply()

    var id: String?
        get() = preference.getString("id", null)
        set(value) = preference.edit().putString("id", value).apply()
}