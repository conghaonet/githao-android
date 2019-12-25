package com.app2m.githaoa.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app2m.githaoa.R
import com.app2m.githaoa.adapter.StarredItemAdapter
import com.app2m.githaoa.databinding.FragmentStarredReposBinding
import com.app2m.githaoa.network.RetrofitClient
import com.app2m.githaoa.network.SharedPreferencesUtil
import com.app2m.githaoa.network.data.RepoData
import com.app2m.githaoa.vm.RepoItemVM
import kotlinx.coroutines.*
import retrofit2.Response

class StarredReposFrag : BaseFragment() {
    private val vmResults: MutableList<RepoItemVM> = ArrayList()
    private lateinit var adapter: StarredItemAdapter
    private lateinit var mBinding: FragmentStarredReposBinding
    private var isLastPage = false
    private var requestJob: Job? = null
    companion object {
        private const val PAGE_SIZE = 30
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
        initRecyclerView()
        loadData(0)
        return mBinding.root
    }

    private fun initRecyclerView() {
        mBinding!!.swipeRefreshLayout.setOnRefreshListener {
            loadData(0)
        }
        mBinding!!.recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        adapter = StarredItemAdapter(vmResults)
        mBinding!!.recyclerView.adapter = adapter
        mBinding!!.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var lastVisibleItem: Int = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.itemCount) {
                    if (adapter.getRealItemCount() % PAGE_SIZE == 0) {
                        loadData(vmResults.size)
                    } else {
                        Toast.makeText(recyclerView.context, "没有更多数据！", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (isLastPage) {
//                        adapter.setFootView(R.layout.no_more_footer)
                    } else {
                        adapter.setFootView()
                    }
                }
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                //最后一个可见的ITEM
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            }
        })
    }

    private fun loadData(offset: Int) {
        if (offset == 0 && !mBinding!!.swipeRefreshLayout.isRefreshing) {
            mBinding!!.swipeRefreshLayout.isRefreshing = true
        }
        var pageNo = (offset / PAGE_SIZE) + 1
        requestJob = GlobalScope.launch(Dispatchers.Main) {
            SharedPreferencesUtil.getLoginUserData()?.let {
                try {
//                    val myReposResponse: Response<List<RepoData>> = RetrofitClient.reqApi.getStarredRepos(it.login, page = pageNo)
                    val myReposResponse: Response<List<RepoData>> = RetrofitClient.reqApi.getMyRepos(page = pageNo, type = "public")
                    if (myReposResponse.isSuccessful) {
                        Toast.makeText(this@StarredReposFrag.context, "length = ${myReposResponse.body()?.size}", Toast.LENGTH_SHORT).show()
                        isLastPage = myReposResponse.body()!!.size < PAGE_SIZE
                        setViewModel(offset, myReposResponse.body()!!)
                    } else {
                        Toast.makeText(this@StarredReposFrag.context, myReposResponse.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@StarredReposFrag.context, e.toString(), Toast.LENGTH_LONG).show()
                } finally {
                    mBinding!!.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    private fun setViewModel(offset: Int, results: List<RepoData>) {
        if (offset == 0) {
            vmResults.clear()
        }
        if (isLastPage) {
            adapter.setFootView(null)
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