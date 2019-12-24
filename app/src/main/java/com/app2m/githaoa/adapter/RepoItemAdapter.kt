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


class RepoItemAdapter(private val list: List<RepoItemVM>) : RecyclerView.Adapter<RepoItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.repo_item, parent, false
        )
        binding.setVariable(BR.adapter, this)
        val holder = ViewHolder(binding.root)
        holder.binding = binding
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.setVariable(BR.model, list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ViewDataBinding? = null

    }

}