package com.app2m.githaoa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.app2m.githaoa.R
import com.app2m.githaoa.databinding.FragmentStarredReposBinding

class StarredReposFrag : BaseFragment() {
    private lateinit var mBinding: FragmentStarredReposBinding

    companion object {
        fun newInstance() = StarredReposFrag()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_starred_repos, container, false)
            mBinding.lifecycleOwner = this
        }
        return mBinding.root
    }
}