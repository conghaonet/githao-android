package com.app2m.githaoa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.app2m.githaoa.BR
import com.app2m.githaoa.R
import com.app2m.githaoa.databinding.StarredItemBinding
import com.app2m.githaoa.vm.RepoItemVM


class StarredItemAdapter(private val list: List<RepoItemVM>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var footerLayoutId: Int? = null
    companion object {
        private const val TYPE_FOOTER = -2147483648
        private const val DEFAULT_FOOTER_LAYOUT_ID = R.layout.load_more_footer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_FOOTER -> {
                val footerView = LayoutInflater.from(parent.context).inflate(footerLayoutId!!, parent, false)
                FooterViewHolder(footerView)
            }
            else -> {
                val binding: ViewDataBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.starred_item, parent, false
                )
                binding.setVariable(BR.adapter, this)
                RepoViewHolder(binding)
            }
        }
    }

    override fun getItemCount() = if(footerLayoutId == null) list.size else list.size + 1
    fun getRealItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return if (footerLayoutId != null && position + 1 == itemCount) {
            TYPE_FOOTER
        } else {
            return super.getItemViewType(position)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < list.size) {
            onBindRealItem(holder as RepoViewHolder, position)
        } else if (footerLayoutId != null && position + 1 == list.size) {
            onBindFooter(holder as FooterViewHolder, position)
        }
    }

    fun onBindFooter(holder: FooterViewHolder, position: Int) {

    }

    fun setFootView(@LayoutRes footerId: Int? = DEFAULT_FOOTER_LAYOUT_ID) {
        if (footerId != footerLayoutId) {
            footerLayoutId = footerId
            this.notifyDataSetChanged()
        }
    }

    private fun onBindRealItem(holder: RepoViewHolder, position: Int) {
        holder.binding.setVariable(BR.model, list[position])
        //item内部点击事件
        (holder.binding as StarredItemBinding).userAvatar.setOnClickListener {
            Toast.makeText(it.context, list[position].repo.owner.login, Toast.LENGTH_SHORT).show()
        }
        //防止刷新闪烁
        holder.binding.executePendingBindings()
    }

    class RepoViewHolder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        val binding = viewDataBinding

    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

}