package com.app2m.githaoa.fragment

import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.app2m.githaoa.R
import com.app2m.githaoa.adapter.RepoItemAdapter
import com.app2m.githaoa.databinding.FragmentMyReposBinding
import com.app2m.githaoa.network.RetrofitClient
import com.app2m.githaoa.network.data.RepoData
import com.app2m.githaoa.vm.RepoItemVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class MyReposFrag: BaseFragment() {
    private val vmResults: MutableList<RepoItemVM> = ArrayList()
    private lateinit var adapter: RepoItemAdapter
    private var mBinding: FragmentMyReposBinding? = null
    private var requestJob: Job? = null
    companion object {
        const val PAGE_SIZE = 30
        private const val TAG = "MyReposFrag"
        fun newInstance(): MyReposFrag {
            return MyReposFrag()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate savedInstanceState = null is ${savedInstanceState == null}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView savedInstanceState = null is ${savedInstanceState == null}")
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_repos, container, false)
            mBinding!!.lifecycleOwner = this
            initRecyclerView()
            loadData(0)
        }
        return mBinding!!.root
    }

    private fun initRecyclerView() {
        mBinding!!.swipeRefreshLayout.setOnRefreshListener {
            loadData(0)
        }
        mBinding!!.recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        adapter = RepoItemAdapter(vmResults)
        mBinding!!.recyclerView.adapter = adapter
        mBinding!!.recyclerView.addOnScrollListener(object : OnScrollListener() {
            var lastVisibleItem: Int = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.itemCount) {
                    if (adapter.itemCount % PAGE_SIZE == 0) {
                        loadData(vmResults.size)
                    } else {
                        Toast.makeText(recyclerView.context, "没有更多数据！", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                //最后一个可见的ITEM
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged")
    }

    private fun loadData(offset: Int) {
        if (offset == 0 && !mBinding!!.swipeRefreshLayout.isRefreshing) {
            mBinding!!.swipeRefreshLayout.isRefreshing = true
        }
        var pageNo = (offset / PAGE_SIZE) + 1
        requestJob = GlobalScope.launch(Dispatchers.Main) {
            try {
                val myReposResponse: Response<List<RepoData>> = RetrofitClient.reqApi.getMyRepos(page = pageNo, type = "public")
                if (myReposResponse.isSuccessful) {
                    Toast.makeText(this@MyReposFrag.context, "length = ${myReposResponse.body()?.size}", Toast.LENGTH_SHORT).show()
                    setViewModel(offset, myReposResponse.body()!!)
                } else {
                    Toast.makeText(this@MyReposFrag.context, myReposResponse.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MyReposFrag.context, e.toString(), Toast.LENGTH_LONG).show()
            } finally {
                mBinding!!.swipeRefreshLayout.isRefreshing = false
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

    override fun onDestroy() {
        requestJob?.cancel()
        super.onDestroy()
    }
}