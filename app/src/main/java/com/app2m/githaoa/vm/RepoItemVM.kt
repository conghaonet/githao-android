package com.app2m.githaoa.vm

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.*
import com.app2m.githaoa.network.data.RepoData

class RepoItemVM(@NonNull application: Application, val repo: RepoData): AndroidViewModel(application), LifecycleObserver {
    val name: MutableLiveData<String> = MutableLiveData(repo.name)
    val avatarUrl: MutableLiveData<String> = MutableLiveData(repo.owner.avatarUrl)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(LoginVM.TAG, "RepoItemVM is ON_RESUME, repoName = ${repo.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(LoginVM.TAG, "RepoItemVM is ON_PAUSE, repoName = ${repo.name}")
    }

}