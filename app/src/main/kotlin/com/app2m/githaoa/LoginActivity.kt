package com.app2m.githaoa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app2m.githaoa.databinding.ActivityLoginBinding
import com.app2m.githaoa.network.DataBean
import com.app2m.githaoa.network.RetrofitClient
import com.app2m.githaoa.vm.LoginVM
import kotlinx.coroutines.*
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var loginVM: LoginVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginVM = ViewModelProviders.of(this).get(LoginVM::class.java)
        binding.model = loginVM
        binding.lifecycleOwner = this

        this.lifecycle.addObserver(loginVM)

        loginVM.tips.postValue("init value")
        loginVM.username.observe(this, Observer<String> { username ->
            Toast.makeText(this, "username: $username", Toast.LENGTH_SHORT).show()
            loginVM.tips.value = username
        })
        loginVM.password.observe(this, Observer<String> { password ->
            Toast.makeText(this, "password: $password", Toast.LENGTH_SHORT).show()
//            loginVM.tips.value = password
        })


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

/*
            val result = withContext(Dispatchers.IO){
//                RetrofitClient.reqApi.getDataAsync().await()
                try {
                    RetrofitClient.reqApi.getDataAsync()
                } catch (e: Exception) {
                }

            }
*/
        }

    }
}
