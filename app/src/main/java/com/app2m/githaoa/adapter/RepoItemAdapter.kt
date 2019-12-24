package com.app2m.githaoa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.app2m.githaoa.BR
import com.app2m.githaoa.R
import com.app2m.githaoa.vm.RepoItemVM


class RepoItemAdapter(private val list: List<RepoItemVM>) : RecyclerView.Adapter<RepoItemAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.repo_item, parent, false
        )
        binding.setVariable(BR.adapter, this)
        return RepoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.binding.setVariable(BR.model, list[position])
        //防止刷新闪烁
        holder.binding.executePendingBindings()
    }

    class RepoViewHolder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        val binding = viewDataBinding

    }

}