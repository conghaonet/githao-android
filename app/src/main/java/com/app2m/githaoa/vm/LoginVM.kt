package com.app2m.githaoa.vm

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.app2m.githaoa.network.SharedPreferencesUtil
import com.app2m.githaoa.network.RetrofitClient
import com.app2m.githaoa.network.data.AuthorizationData
import com.app2m.githaoa.network.data.UserData
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception


class LoginVM(context: Application): AndroidViewModel(context), LifecycleObserver {
    companion object {
        const val TAG = "LoginVM"
    }
    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val tips: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(TAG, "LoginVM is ON_RESUME")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG, "LoginVM is ON_PAUSE")

    }
    fun doLogin(view: View) {
        if (loading.value != null && loading.value!!) {
            return
        }

        Log.d(TAG, "username = ${username.value}")
        SharedPreferencesUtil.removeAuth()
        if (!username.value.isNullOrBlank() && !password.value.isNullOrBlank()) {
            SharedPreferencesUtil.putLoginName(username.value!!)
            SharedPreferencesUtil.putAuthorizationBasic(username.value!!, password.value!!)
            viewModelScope.launch {
                loading.value = true
                try {
                    val authResponse: Response<AuthorizationData> = RetrofitClient.reqApi.postAuthorizations()
                    if (authResponse.isSuccessful) {
                        val loginResponse: Response<UserData> = RetrofitClient.reqApi.getLoginUser()
                        if (loginResponse.isSuccessful) {
                            SharedPreferencesUtil.putLoginUserData(loginResponse.body())
                        } else {
                            Toast.makeText(this@LoginVM.getApplication(), loginResponse.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginVM.getApplication(), authResponse.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginVM.getApplication(), e.toString(), Toast.LENGTH_SHORT).show()
                } finally {
                    loading.value = false
                    Toast.makeText(this@LoginVM.getApplication(), "登录完成完成", Toast.LENGTH_SHORT).show()
                }
            }
        }

/*
        viewModelScope.launch {
            for (index in 1..100){
                delay(300)
                Log.d(TAG, "index = $index")
                tips.postValue("index = $index")
            }
        }
*/

    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "LoginVM is cleared")
    }
}