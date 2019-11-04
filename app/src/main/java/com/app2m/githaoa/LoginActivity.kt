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
import android.view.View
import android.widget.Toast
import com.app2m.githaoa.network.RetrofitClient
import com.app2m.githaoa.network.SharedPreferencesUtil
import com.app2m.githaoa.network.data.AuthorizationData
import com.app2m.githaoa.network.data.UserData
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception


class LoginActivity : AppCompatActivity() {
    private lateinit var loginVM: LoginVM
    private val animatorLoading = AnimatorSet()//组合动画
    private var loginJob: Job? = null
    private lateinit var binding: ActivityLoginBinding
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginVM = ViewModelProviders.of(this).get(LoginVM::class.java)
        binding.model = loginVM
        binding.lifecycleOwner = this

        this.lifecycle.addObserver(loginVM)

        loginVM.tips.postValue("init value")
        loginVM.username.observe(this, Observer<String> { username ->
//            Toast.makeText(this, "username: $username", Toast.LENGTH_SHORT).show()
            loginVM.tips.value = username
        })
    }

    fun onClickLogin(view: View) {
        if (isLoading) return
        if (validField()) {
            playAnimation()
            SharedPreferencesUtil.putLoginName(loginVM.username.value!!)
            SharedPreferencesUtil.putAuthorizationBasic(loginVM.username.value!!, loginVM.password.value!!)
            loginJob = GlobalScope.launch(Dispatchers.Main) {
                try {
                    val authResponse: Response<AuthorizationData> = RetrofitClient.reqApi.postAuthorizations()
                    if (authResponse.isSuccessful) {
                        val loginResponse: Response<UserData> = RetrofitClient.reqApi.getLoginUser()
                        if (loginResponse.isSuccessful) {
                            SharedPreferencesUtil.putLoginUserData(loginResponse.body())
                            Toast.makeText(this@LoginActivity, R.string.login_successful, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@LoginActivity, loginResponse.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, authResponse.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    if(e !is CancellationException) {
                        Toast.makeText(this@LoginActivity, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                } finally {
                    resetLoginButton()

                }
            }
        }
    }

    private fun validField(): Boolean {
        var isValid = true
        if (loginVM.username.value.isNullOrBlank()) {
            binding.userNameLayout.isErrorEnabled = true
            binding.passwordLayout.error = resources.getString(R.string.this_field_cannot_be_empty)
            isValid = false
        } else {
            binding.userNameLayout.isErrorEnabled = false
        }
        if (loginVM.password.value.isNullOrBlank()) {
            binding.passwordLayout.isErrorEnabled = true
            binding.passwordLayout.error = resources.getString(R.string.this_field_cannot_be_empty)
            isValid = false
        } else {
            binding.passwordLayout.isErrorEnabled = false
        }
        return isValid
    }

    private fun playAnimation() {
        animatorLoading.cancel()

        val animatorButton = AnimatorSet()
        val scaleX = ObjectAnimator.ofFloat(binding.loginBtn, "scaleX", 1f, 0f)
        val scaleY = ObjectAnimator.ofFloat(binding.loginBtn, "scaleY", 1f, 0f)
        animatorButton.duration = 300
        animatorButton.interpolator = AccelerateInterpolator()
        animatorButton.play(scaleX).with(scaleY) //两个动画同时开始

        val loadingAlpha = ObjectAnimator.ofFloat(binding.loginLoadingBar, "alpha", 0f, 1f)
        loadingAlpha.duration = 200

        animatorLoading.play(animatorButton).before(loadingAlpha)
        animatorLoading.play(loadingAlpha)
        animatorLoading.start()
    }

    private fun resetLoginButton() {
        animatorLoading.cancel()
        binding.loginBtn.scaleX = 1f
        binding.loginBtn.scaleY = 1f
    }

    override fun onDestroy() {
        loginJob?.cancel()
        animatorLoading.cancel()
        super.onDestroy()
    }
}
