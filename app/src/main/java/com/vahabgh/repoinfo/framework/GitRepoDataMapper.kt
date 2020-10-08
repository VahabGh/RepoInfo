package com.vahabgh.repoinfo.framework

import android.annotation.SuppressLint
import android.util.Log
import com.vahabgh.core.data.ResponseData
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.repoinfo.GetFirstListOfRepositoriesQuery
import com.vahabgh.repoinfo.GetListOfRepoQuery
import com.vahabgh.repoinfo.presentation.db.GitRepoEntity
import java.text.SimpleDateFormat
import java.util.*

class GitRepoDataMapper {

    fun mapRepoListFromServerFirst(edges: List<GetFirstListOfRepositoriesQuery.Edge?>?): List<GitRepo>? {
        return edges?.mapNotNull {
            it?.node?.asRepository?.let { repoItem ->
                GitRepo(
                    repoItem.id,
                    repoItem.nameWithOwner,
                    repoItem.name,
                    repoItem.createdAt as? String ?: " - ",
                    convertDateToMillis(repoItem.createdAt as? String),
                    repoItem.description ?: "",
                    repoItem.forkCount,
                    repoItem.stargazerCount,
                    null,
                    repoItem.url as? String ?: ""
                )
            }
        }
    }

    fun mapRepoListFromServer(edges: List<GetListOfRepoQuery.Edge?>?): List<GitRepo>? {
        return edges?.mapNotNull {
            it?.node?.asRepository?.let { repoItem ->
                GitRepo(
                    repoItem.id,
                    repoItem.nameWithOwner,
                    repoItem.name,
                    repoItem.createdAt as? String ?: " - ",
                    convertDateToMillis(repoItem.createdAt as? String),
                    repoItem.description ?: "",
                    repoItem.forkCount,
                    repoItem.stargazerCount,
                    null,
                    repoItem.url as? String ?: ""
                )
            }
        }
    }

    fun mapDataFromDb(localData: List<GitRepoEntity>): ResponseData<List<GitRepo>> {
        val mappedItem = localData.map { repoItem ->
            GitRepo(
                repoItem.repoId,
                repoItem.ownerName,
                repoItem.repoName,
                repoItem.createDate,
                convertDateToMillis(repoItem.createDate),
                repoItem.description,
                repoItem.forkCount,
                repoItem.starCount,
                null,
                repoItem.repoUrl
            )
        }

        return ResponseData.success(mappedItem)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateToMillis(createDate: String?): Long {
        if (createDate == null) return 0
        Log.i("convert",createDate)
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        parser.timeZone = TimeZone.getTimeZone("UTC")
        parser.parse(createDate)
        Log.i("convert2",parser.calendar.timeInMillis.toString())
        return parser.calendar.timeInMillis
    }


}