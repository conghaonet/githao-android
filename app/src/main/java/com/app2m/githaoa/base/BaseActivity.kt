package com.app2m.githaoa.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app2m.githaoa.R

open class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        this.setTheme(R.style.AppThemeLight)
        super.onCreate(savedInstanceState)
    }
}