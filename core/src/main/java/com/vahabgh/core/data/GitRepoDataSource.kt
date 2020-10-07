package com.vahabgh.core.data

import com.vahabgh.core.domain.GitRepo
import kotlinx.coroutines.flow.Flow

interface GitRepoDataSource {

    suspend fun getFirstItems(firstItemCount : Int) : Flow<ResponseData<List<GitRepo>>>

    suspend fun saveRepos(pageIndex : Int,repos: List<GitRepo>) : Flow<Boolean>

    suspend fun getRepos(pageIndex : Int) : Flow<ResponseData<List<GitRepo>>>

    suspend fun getRepoById(id: String): Flow<ResponseData<GitRepo>>
}