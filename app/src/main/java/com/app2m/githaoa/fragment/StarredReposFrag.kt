package com.app2m.githaoa.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.app2m.githaoa.R
import com.app2m.githaoa.databinding.FragmentStarredReposBinding
import kotlinx.coroutines.*

class StarredReposFrag : BaseFragment() {
    private lateinit var mBinding: FragmentStarredReposBinding
    private var testingJob: Job? = null
    companion object {
        private const val TAG = "StarredReposFrag"
        fun newInstance() = StarredReposFrag()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate savedInstanceState == null is ${savedInstanceState == null}")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView savedInstanceState == null is ${savedInstanceState == null}")
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_starred_repos, container, false)
        mBinding.lifecycleOwner = this
        testing()
        return mBinding.root
    }



    private fun testing() {
        testingJob = GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            Toast.makeText(this@StarredReposFrag.context, "StarredReposFrag 测试！", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        testingJob?.cancel()
        super.onDestroy()
    }
}