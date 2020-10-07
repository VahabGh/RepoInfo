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

//        data.apply {
//            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//            parser.timeZone = TimeZone.getTimeZone("UTC")
//            Log.i(
//                "ConvertDate",
//                "${this.createDate} -> " + parser.parse(this.createDate).toString() + "time in milisecond : ${parser.calendar.timeInMillis}"
//            )
//        }


    }

}