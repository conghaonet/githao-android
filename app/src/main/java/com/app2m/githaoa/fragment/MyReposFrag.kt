package com.app2m.githaoa.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.app2m.githaoa.R
import com.app2m.githaoa.adapter.RepoItemAdapter
import com.app2m.githaoa.databinding.FragmentReposBinding
import com.app2m.githaoa.network.RetrofitClient
import com.app2m.githaoa.network.data.RepoData
import com.app2m.githaoa.vm.RepoItemVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MyReposFrag: BaseFragment() {
    private val vmResults: MutableList<RepoItemVM> = ArrayList()
    private lateinit var adapter: RepoItemAdapter
    private lateinit var mBinding: FragmentReposBinding

    companion object {
        fun newInstance(): MyReposFrag {
            return MyReposFrag()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_repos, container, false)
            mBinding.lifecycleOwner = this
            initRecyclerView()
            loadData(1)
        }
        return mBinding.root
    }

    private fun initRecyclerView() {
        mBinding.swipeRefreshLayout.setOnRefreshListener {
            loadData(1)
        }
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        adapter = RepoItemAdapter(vmResults)
        mBinding.recyclerView.adapter = adapter

    }

    private fun loadData(offset: Int) {
        if (offset == 1 && !mBinding.swipeRefreshLayout.isRefreshing) {
            mBinding.swipeRefreshLayout.isRefreshing = true
        }
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val myReposResponse: Response<List<RepoData>> = RetrofitClient.reqApi.getMyRepos(page = offset)
                if (myReposResponse.isSuccessful) {
                    Toast.makeText(this@MyReposFrag.context, "length = ${myReposResponse.body()?.size}", Toast.LENGTH_SHORT).show()
                    setViewModel(offset, myReposResponse.body()!!)
                } else {
                    Toast.makeText(this@MyReposFrag.context, myReposResponse.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MyReposFrag.context, e.toString(), Toast.LENGTH_LONG).show()
            } finally {
                mBinding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun setViewModel(offset: Int, results: List<RepoData>) {
        if (offset == 0) {
            vmResults.clear()
            adapter.notifyDataSetChanged()
        }
        for (item in results) {
            val itemModel = RepoItemVM(Application(), item)
            this.lifecycle.addObserver(itemModel)
            vmResults.add(itemModel)
        }
        adapter.notifyDataSetChanged()
    }
}