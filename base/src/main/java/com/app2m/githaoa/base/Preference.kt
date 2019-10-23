package com.app2m.githaoa.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

/**
 *
 */

@Deprecated("This class is deprecated.", replaceWith = ReplaceWith("PreferenceUtil"))
class Preference<T>(appCtx: Context, val name: String, private val default: T) {

    private val prefs: SharedPreferences by lazy { appCtx.applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE) }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharePreferences(name, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putSharePreferences(name, value)
    }

    @SuppressLint("CommitPrefEdits")
    private fun putSharePreferences(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Int -> putInt(name, value)
            is Long -> putLong(name, value)
            is Float -> putFloat(name, value)
            is String -> putString(name, value)
            is Boolean -> putBoolean(name, value)
            else -> throw IllegalArgumentException("This type of data cannot be saved!")
        }.apply() //commit方法和apply方法都表示提交修改
    }

    @Suppress("UNCHECKED_CAST")
    private fun getSharePreferences(name: String, default: T): T = with(prefs) {
        val res: Any? = when (default) {
            is Int -> getInt(name, default)
            is Long -> getLong(name, default)
            is Float -> getFloat(name, default)
            is String -> getString(name, default)
            is Boolean -> getBoolean(name, default)
            else -> throw IllegalArgumentException("This type of data cannot be saved!")
        }
        return res as T
    }

}