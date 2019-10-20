package com.app2m.githaoa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun gotoTryDataBindingActivity(view: View) {
        startActivity(Intent(this, TryDataBindingActivity::class.java))
    }

    fun doLogin(view: View) {
//        startActivity(Intent(this, LoginActivity::class.java))
    }
}
