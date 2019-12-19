package com.app2m.githaoa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class TryDayNightActivity : AppCompatActivity() {
    companion object {
        const val MODE_NIGHT_ACTION = "com.app2m.intent.mode_night_action"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_try_day_night)
    }

    fun onClickDay(view: View) {
        switchDayNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    fun onClickNight(view: View) {
        switchDayNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
    fun onClickFollowSystem(view: View) {
        switchDayNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
    private fun switchDayNightMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(MODE_NIGHT_ACTION))
//        delegate.localNightMode = mode
//        recreate()

    }
    fun openMain2Activity(view: View) {
//        startActivity(Intent(this, ComponentsActivity::class.java))
    }
}
