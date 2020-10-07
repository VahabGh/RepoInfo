package com.vahabgh.repoinfo.presentation.ui.repolist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.repoinfo.R
import com.vahabgh.repoinfo.databinding.ViewHolderFooterLoadingBinding
import com.vahabgh.repoinfo.databinding.ViewHolderRepoItemBinding
import com.vahabgh.repoinfo.presentation.db.GitRepoEntity
import java.lang.IllegalArgumentException

class ReposAdapter(
    private val items: MutableList<Any>,
    private val onItemClick: (GitRepo) -> Unit,
    private val retryDelegate: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == R.layout.view_holder_footer_loading)
            return FooterViewHolder(
                ViewHolderFooterLoadingBinding.inflate(inflater, parent, false),
                retryDelegate
            )
        if (viewType == R.layout.view_holder_repo_item)
            return RepoItemViewHolder(
                ViewHolderRepoItemBinding.inflate(inflater, parent, false),
                onItemClick
            )
        throw IllegalArgumentException("There is no viewHolder match this type")
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FooterViewHolder)
            holder.bind(items[position] as FooterLoading)
        if (holder is RepoItemViewHolder)
            holder.bind(items[position] as GitRepo)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FooterLoading -> R.layout.view_holder_footer_loading
            is GitRepo -> R.layout.view_holder_repo_item
            else -> throw IllegalArgumentException("There is no match type viewHolder with this item position")
        }
    }

    fun addItems(newItems: MutableList<Any>) {
        if (newItems.isNotEmpty()) {
            val lastIndex = items.lastIndex
            items.addAll(lastIndex, newItems)
            notifyItemRangeInserted(lastIndex, newItems.size)
        }
    }

    fun addFooter() {
        if (items.last() !is FooterLoading)
            items.add(FooterLoading(false))
    }

    fun removeFooter() {
        if (items.last() is FooterLoading) {
            val lastIndex = items.lastIndex
            items.removeAt(lastIndex)
        }
    }

    fun showErrorFooter(errorMessage: String) {
        if (items.last() is FooterLoading) {
            val lastitem = items.last() as FooterLoading
            lastitem.hasError = true
            lastitem.errMessage = errorMessage
            notifyItemChanged(items.lastIndex)
        }
    }

    fun hideFooterError() {
        if (items.last() is FooterLoading) {
            val lastitem = items.last() as FooterLoading
            lastitem.hasError = false
            lastitem.errMessage = ""
            notifyItemChanged(items.lastIndex)
        }
    }
}