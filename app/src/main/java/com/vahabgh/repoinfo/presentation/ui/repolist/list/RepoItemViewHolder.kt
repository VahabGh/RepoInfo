package com.vahabgh.repoinfo.presentation.ui.repolist.list

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.repoinfo.BR
import com.vahabgh.repoinfo.databinding.ViewHolderRepoItemBinding
import java.text.SimpleDateFormat
import java.util.*

class RepoItemViewHolder(
    private val binding: ViewHolderRepoItemBinding,
    private val onItemClick: (GitRepo) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {

        itemView.setOnClickListener {
            onItemClick.invoke(binding.listItem as GitRepo)
        }

    }

    fun bind(data: GitRepo) {
        with(binding) {
            setVariable(BR.listItem, data)
            executePendingBindings()
        }
    }

}