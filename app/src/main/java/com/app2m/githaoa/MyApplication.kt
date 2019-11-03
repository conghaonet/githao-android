package com.app2m.githaoa

import androidx.multidex.MultiDexApplication
import com.app2m.githaoa.network.SharedPreferencesUtil

class MyApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtil.initial(this)
    }
}