package com.app2m.githaoa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app2m.githaoa.TryDayNightActivity.Companion.MODE_NIGHT_ACTION
import com.app2m.githaoa.base.BaseActivity
import com.app2m.githaoa.databinding.ActivityHomeBinding


class HomeActivity : BaseActivity() {
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private val mBinding : ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
    }
    private val dayNightReceiver by lazy {
        DayNightReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.lifecycleOwner = this
        LocalBroadcastManager.getInstance(this).registerReceiver(dayNightReceiver, IntentFilter(MODE_NIGHT_ACTION))
        setSupportActionBar(mBinding.homeToolBar)
//        mBinding.homeDrawerLayout.setStatusBarBackgroundColor(Color.RED)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = ActionBarDrawerToggle(this, mBinding.homeDrawerLayout, mBinding.homeToolBar, R.string.app_name, R.string.login)

        mDrawerToggle.syncState()
        mBinding.homeDrawerLayout.addDrawerListener(mDrawerToggle)

    }
    fun gotoDayNightActivity(view: View) {
        startActivity(Intent(this, TryDayNightActivity::class.java))
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
