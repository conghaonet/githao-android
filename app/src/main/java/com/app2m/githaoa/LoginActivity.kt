package com.app2m.githaoa

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app2m.githaoa.databinding.ActivityLoginBinding
import com.app2m.githaoa.vm.LoginVM

class LoginActivity : AppCompatActivity() {
    private lateinit var loginVM: LoginVM
    private val animatorLoading = AnimatorSet()//组合动画
    private var scaleX: ObjectAnimator? = null
    private var scaleY: ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginVM = ViewModelProviders.of(this).get(LoginVM::class.java)
        binding.model = loginVM
        binding.lifecycleOwner = this

        this.lifecycle.addObserver(loginVM)

        loginVM.loading.observe(this, Observer<Boolean>{
            if (it) {
                animatorLoading.cancel()
                scaleX = ObjectAnimator.ofFloat(binding.loginBtn, "scaleX", 1f, 0f)
                scaleY = ObjectAnimator.ofFloat(binding.loginBtn, "scaleY", 1f, 0f)
                animatorLoading.duration = 1000
                animatorLoading.interpolator = AccelerateInterpolator()
                animatorLoading.play(scaleX).with(scaleY) //两个动画同时开始
                animatorLoading.start()
            } else {
                animatorLoading.cancel()
                binding.loginBtn.scaleX = 1f
                binding.loginBtn.scaleY = 1f
            }
        })

        loginVM.tips.postValue("init value")
        loginVM.username.observe(this, Observer<String> { username ->
//            Toast.makeText(this, "username: $username", Toast.LENGTH_SHORT).show()
            loginVM.tips.value = username
        })


/*
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = RetrofitClient.reqApi.getUserAsync()
                if (result.isSuccessful) {
                    Toast.makeText(this@LoginActivity, result.body()?.login, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, result.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
*/

    }

    override fun onDestroy() {
        animatorLoading.cancel()
        super.onDestroy()
    }
}
