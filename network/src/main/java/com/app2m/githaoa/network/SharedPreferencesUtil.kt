package com.app2m.githaoa.network

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.app2m.githaoa.network.data.UserData
import com.google.gson.Gson
import okhttp3.Credentials

@SuppressLint("StaticFieldLeak")
object SharedPreferencesUtil {
    private const val KEY_LOGIN_NAME = "loginName"
    private const val KEY_USER_DATA = "userData"
    private const val KEY_AUTH_BASIC = "authBasic"
    private const val KEY_AUTH_TOKEN = "authToken"

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


    fun getLoginName(default : String? = null): String? {
        return getSP()
            .getString(KEY_LOGIN_NAME, default)
    }
    fun putLoginName(login: String) {
        getSP()
            .edit().putString(KEY_LOGIN_NAME, login).apply()
    }

    fun getAuthorizationBasic(default : String = ""): String? {
        return getSP()
            .getString(KEY_AUTH_BASIC, default)
    }
    fun putAuthorizationBasic(loginName: String, password: String) {
        val authBasic: String = Credentials.basic(loginName, password)
        getSP()
            .edit().putString(KEY_AUTH_BASIC, authBasic).apply()
    }

    fun getLoginUserData(): UserData? {
        val json = getSP().getString(KEY_USER_DATA, null)
        var user: UserData? = null
        json?.let {
            user = Gson().fromJson(it, UserData::class.java)
        }
        return user
    }
    fun putLoginUserData(user: UserData?) {
        user?.let {
            getSP().edit().putString(KEY_USER_DATA, Gson().toJson(it)).apply()
        }
    }

    fun removeAuth() {
        val edit = getSP().edit()
        edit.remove(KEY_LOGIN_NAME)
        edit.remove(KEY_USER_DATA)
        edit.remove(KEY_AUTH_BASIC)
        edit.remove(KEY_AUTH_TOKEN)
        edit.apply()
    }

}