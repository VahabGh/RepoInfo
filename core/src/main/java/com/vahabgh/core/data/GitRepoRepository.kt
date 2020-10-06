package com.vahabgh.core.data

import com.vahabgh.core.domain.GitRepo
import com.vahabgh.core.domain.PageInfo

class GitRepoRepository(private val gitRepoDataSource: GitRepoDataSource) {

    suspend fun getFirstItems(firstItemCount : Int) = gitRepoDataSource.getFirstItems(firstItemCount)

    suspend fun getRepos(pageIndex : Int) = gitRepoDataSource.getRepos(pageIndex)

    suspend fun saveRepos(pageIndex : Int,repos : List<GitRepo>) = gitRepoDataSource.saveRepos(pageIndex,repos)

    suspend fun getRepoById(id : String) = gitRepoDataSource.getRepoById(id)
}