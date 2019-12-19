package com.app2m.githaoa

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app2m.githaoa.base.BaseActivity
import com.app2m.githaoa.databinding.ActivityTryDataBindingBinding

class TryDataBindingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_try_data_binding)
        val binding: ActivityTryDataBindingBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_try_data_binding)
        binding.tryDataBindingText1.text = "aaaaaaaaaaaa"
    }
}
