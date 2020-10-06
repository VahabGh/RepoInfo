package com.vahabgh.core.data

import com.vahabgh.core.domain.GitRepo
import com.vahabgh.core.domain.GitRepoData
import com.vahabgh.core.domain.PageInfo
import kotlinx.coroutines.flow.Flow

interface GitRepoDataSource {

    suspend fun getFirstItems(firstItemCount : Int) : Flow<ResponseData<GitRepoData>>

    suspend fun saveRepos(pageIndex : Int,repos: List<GitRepo>) : Flow<Boolean>

    suspend fun getRepos(pageIndex : Int) : Flow<ResponseData<GitRepoData>>

    suspend fun getRepoById(id: String): Flow<ResponseData<GitRepo>>
}