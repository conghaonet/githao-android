package com.app2m.githaoa.vm

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginVM: ViewModel(), LifecycleObserver {
    companion object {
        const val TAG = "LoginVM"
    }
    val username: MutableLiveData<String> = MutableLiveData("conghao")
    val password: MutableLiveData<String> = MutableLiveData()
    val tips: MutableLiveData<String> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(TAG, "LoginVM is ON_RESUME")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG, "LoginVM is ON_PAUSE")

    }
    fun doLogin() {
        Log.d(TAG, "username = ${username.value}")
        viewModelScope.launch {
            for (index in 1..100){
                delay(300)
                Log.d(TAG, "index = $index")
                tips.postValue("index = $index")
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "LoginVM is cleared")
    }
}