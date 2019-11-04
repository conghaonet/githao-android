package com.app2m.githaoa.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*


class LoginVM(context: Application): AndroidViewModel(context), LifecycleObserver {
    companion object {
        const val TAG = "LoginVM"
    }
    val username: MutableLiveData<String> = MutableLiveData("conghaonet")
    val password: MutableLiveData<String> = MutableLiveData("v505")
    val tips: MutableLiveData<String> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(TAG, "LoginVM is ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG, "LoginVM is ON_PAUSE")
    }


    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "LoginVM is cleared")
    }
}