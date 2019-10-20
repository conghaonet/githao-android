package com.app2m.githaoa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app2m.githaoa.databinding.ActivityTryDataBindingBinding

class TryDataBindingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_try_data_binding)
        val binding: ActivityTryDataBindingBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_try_data_binding)
        binding.tryDataBindingText1.text = "aaaaaaaaaaaa"
    }
}
