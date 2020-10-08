package com.vahabgh.repoinfo.presentation.ui.repolist.list

import androidx.recyclerview.widget.RecyclerView
import com.vahabgh.repoinfo.BR
import com.vahabgh.repoinfo.databinding.ViewHolderFooterLoadingBinding

class FooterViewHolder(val binding : ViewHolderFooterLoadingBinding, private val retryDelegate : () -> Unit)
    : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener {
            retryDelegate.invoke()
        }
    }


    fun bind(footerLoading: FooterLoading) {
        with(binding){
            setVariable(BR.listItem,footerLoading)
            executePendingBindings()
        }
    }

}
