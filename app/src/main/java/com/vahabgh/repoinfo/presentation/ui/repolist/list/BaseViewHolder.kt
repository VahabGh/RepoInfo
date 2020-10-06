package com.vahabgh.repoinfo.presentation.ui.repolist.list

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.repoinfo.BR

open class BaseViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {



    open fun bind(data: Any) {
        with(binding){
            setVariable(BR.listItem,data)
            executePendingBindings()
        }
    }
}