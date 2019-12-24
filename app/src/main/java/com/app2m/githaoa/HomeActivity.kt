package com.app2m.githaoa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
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
    private val mBinding : ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
    }
    private val dayNightReceiver by lazy {
        DayNightReceiver()
    }
    private val fragmentManager by lazy {
        supportFragmentManager
    }

    private var myReposFrag: MyReposFrag? = null

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

        setHeader()

        if (savedInstanceState != null) {
            for (frag in fragmentManager.fragments) {
                fragmentManager.beginTransaction().remove(frag).commitAllowingStateLoss()
            }
        }
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content_container, MyReposFrag.newInstance(), MyReposFrag::class.java.name)
//        transaction.replace(R.id.content_container, StarredReposFrag.newInstance(), MyReposFrag::class.java.name)
        transaction.commitAllowingStateLoss()
    }

    private fun setHeader() {
        val headerView = mBinding.navigationView.getHeaderView(0)
        val headerAvatar = headerView.findViewById<AppCompatImageView>(R.id.home_header_avatar)
        SharedPreferencesUtil.getLoginUserData()?.let {
            Glide.with(this).load(it.avatarUrl).apply(RequestOptions.bitmapTransform(CircleCrop())).into(headerAvatar)
        }
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
