package com.app2m.githaoa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.app2m.githaoa.base.PreferenceUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var login = PreferenceUtil.getLogin()

        var loginA = PreferenceUtil.getLogin(default="aaa")

        Log.d("MainActivity", "$login,,,,$loginA")
    }

    fun gotoTryDataBindingActivity(view: View) {
        startActivity(Intent(this, TryDataBindingActivity::class.java))
    }

    fun gotoLogin(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
