package com.vahabgh.repoinfo.presentation.ui.repolist.list

import android.util.Log
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.repoinfo.databinding.ViewHolderRepoItemBinding
import java.text.SimpleDateFormat
import java.util.*

class RepoItemViewHolder(
    private val repoBinding: ViewHolderRepoItemBinding,
    private val onItemClick: (GitRepo) -> Unit
) : BaseViewHolder(repoBinding) {

    init {

        itemView.setOnClickListener {
            onItemClick.invoke(repoBinding.listItem as GitRepo)
        }

    }

    override fun bind(data: Any) {
        super.bind(data)

        (data as GitRepo).apply {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            parser.timeZone = TimeZone.getTimeZone("UTC")
            Log.i("ConvertDate","${this.createDate } -> "+parser.parse(this.createDate).toString())
        }


    }

}