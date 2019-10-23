package com.app2m.githaoa

import androidx.multidex.MultiDexApplication
import com.app2m.githaoa.base.PreferenceUtil

class MyApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        PreferenceUtil.initial(this)
    }
}