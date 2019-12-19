package com.app2m.githaoa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app2m.githaoa.base.BaseActivity
import com.app2m.githaoa.network.SharedPreferencesUtil

class MainActivity : BaseActivity() {
    private val dayNightReceiver by lazy {
        DayNightReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LocalBroadcastManager.getInstance(this).registerReceiver(dayNightReceiver, IntentFilter(
            TryDayNightActivity.MODE_NIGHT_ACTION
        )
        )
        if (savedInstanceState == null) {
            if (SharedPreferencesUtil.getLoginUserData() != null) {
                startActivity(Intent(this, HomeActivity::class.java))
//                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    fun gotoTryDataBindingActivity(view: View) {
        startActivity(Intent(this, TryDataBindingActivity::class.java))
    }

    fun gotoLogin(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.dayNightReceiver)
        super.onDestroy()

    }
    inner class DayNightReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
//            val defaultNightMode: Int = AppCompatDelegate.getDefaultNightMode()
//            Toast.makeText(context, "MainActivity DefaultNightMode = $defaultNightMode", Toast.LENGTH_SHORT).show()
            recreate()
        }
    }

}
