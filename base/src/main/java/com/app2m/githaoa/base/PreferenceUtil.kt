package com.app2m.githaoa.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("StaticFieldLeak")
object PreferenceUtil {
    const val KEY_LOGIN = "login"

    private var applicationContext: Context? = null

    @Synchronized
    fun initial(context: Context) {
        if (applicationContext == null) {
            applicationContext = context.applicationContext
        }
    }

    private fun getSP(): SharedPreferences {
        return applicationContext!!.getSharedPreferences("data", Context.MODE_PRIVATE)
    }
    fun getLogin(default : String? = null): String? {
        return getSP().getString(KEY_LOGIN, default)
    }
    fun putLogin(login: String) {
        getSP().edit().putString(KEY_LOGIN, login).apply()
    }
}