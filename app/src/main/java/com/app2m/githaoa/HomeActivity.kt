package com.app2m.githaoa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app2m.githaoa.TryDayNightActivity.Companion.MODE_NIGHT_ACTION
import com.app2m.githaoa.base.BaseActivity
import com.app2m.githaoa.databinding.ActivityHomeBinding
import com.app2m.githaoa.fragment.MyReposFrag
import com.app2m.githaoa.fragment.StarredReposFrag
import com.app2m.githaoa.network.SharedPreferencesUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


class HomeActivity : BaseActivity() {
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private var checkedItemId = R.id.menu_my_repos
    private val mBinding : ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
    }
    private val dayNightReceiver by lazy {
        DayNightReceiver()
    }
    private val fragmentManager by lazy {
        supportFragmentManager
    }
    companion object {
        private const val TAG = "HomeActivity"
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

        initNavigation()

        if (savedInstanceState != null) {
            for (frag in fragmentManager.fragments) {
                fragmentManager.beginTransaction().remove(frag).commitAllowingStateLoss()
            }
        }
        setContentFragment(MyReposFrag.newInstance())

    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged")
    }

    private fun initNavigation() {
        val headerView = mBinding.navigationView.getHeaderView(0)
        val headerAvatar = headerView.findViewById<AppCompatImageView>(R.id.home_header_avatar)
        SharedPreferencesUtil.getLoginUserData()?.let {
            Glide.with(this).load(it.avatarUrl).apply(RequestOptions.bitmapTransform(CircleCrop())).into(headerAvatar)
        }
        mBinding.navigationView.setCheckedItem(checkedItemId)
        mBinding.navigationView.setNavigationItemSelectedListener {
            if (it.isCheckable) {
                checkedItemId = it.itemId
            }
            when (it.itemId) {
                R.id.menu_my_repos -> setContentFragment(MyReposFrag.newInstance())
                R.id.menu_starred_repos -> setContentFragment(StarredReposFrag.newInstance())
                R.id.menu_day_night_mode -> startActivity(Intent(this, TryDayNightActivity::class.java))
            }
            mBinding.homeDrawerLayout.closeDrawers()
            true
        }

    }
    private fun setContentFragment(fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content_container, fragment)
        transaction.commitAllowingStateLoss()
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
