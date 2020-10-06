package com.vahabgh.repoinfo.framework

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.vahabgh.core.data.GitRepoDataSource
import com.vahabgh.core.data.ResponseData
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.core.domain.GitRepoData
import com.vahabgh.core.domain.PageInfo
import com.vahabgh.repoinfo.GetFirstListOfRepositoriesQuery
import com.vahabgh.repoinfo.GetListOfRepoQuery
import com.vahabgh.repoinfo.presentation.db.GitRepoDatabase
import com.vahabgh.repoinfo.presentation.db.GitRepoEntity
import com.vahabgh.repoinfo.presentation.db.LastPageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GitRepoDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val dataBase: GitRepoDatabase,
    private val firstItemsQuery: GetFirstListOfRepositoriesQuery,
    private val query: GetListOfRepoQuery
) : GitRepoDataSource {

    override suspend fun getFirstItems(firstItemCount: Int): Flow<ResponseData<GitRepoData>> {
        return flow {
            try {
                val response = apolloClient.query(firstItemsQuery).await()
                val repoList =
                    GitRepoDataMapper.mapRepoListFromServerFirst(response.data?.search?.edges)
                val pageInfo =
                    GitRepoDataMapper.getPageInfoFirst(1, response.data?.search?.pageInfo)
                dataBase.gitRepoDao().insertLastPage(LastPageEntity(0, pageInfo?.start ?: "", pageInfo?.end ?: ""))
                emit(
                    ResponseData.success(
                        GitRepoData(pageInfo, repoList)
                    )
                )
            } catch (e: ApolloException) {
                emit(ResponseData.error(e))
            }
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun saveRepos(pageIndex: Int, repos: List<GitRepo>): Flow<Boolean> {
        return flow {
            saveReposInDb(pageIndex,repos)
            emit(true)
        }.flowOn(Dispatchers.IO)
    }

    private fun saveReposInDb(pageIndex: Int, repos: List<GitRepo>) {
        dataBase.gitRepoDao().insertAll(repos.map {
            GitRepoEntity(
                it.id,
                it.ownerName,
                it.repoName,
                it.createDate,
                it.description,
                it.forkCount,
                it.starCount,
                it.repoUrl,
                pageIndex
            )
        })
    }

    override suspend fun getRepos(pageIndex: Int): Flow<ResponseData<GitRepoData>> {
        return flow {
            val localData = dataBase.gitRepoDao().getAllByPageIndex(pageIndex)
            if (localData.isNullOrEmpty()) {
                val remoteData = getDataFromServer()
                remoteData.data?.gitRepos?.let {
                    saveReposInDb(pageIndex, it)
                }
                emit(remoteData)
            } else
                emit(GitRepoDataMapper.mapDataFromDb(localData))
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun getDataFromServer(): ResponseData<GitRepoData> {
        return try {
            val lastPage = dataBase.gitRepoDao().getLastPage(0)
            val newQuery = query.copy(start = lastPage.startC, end = lastPage.endC)
            val response = apolloClient.query(newQuery).await()
            val repoList = GitRepoDataMapper.mapRepoListFromServer(response.data?.search?.edges)
            val pageInfo = GitRepoDataMapper.getPageInfo(1, response.data?.search?.pageInfo)
            dataBase.gitRepoDao().update(0,
                response.data?.search?.pageInfo?.startCursor ?: "",
                response.data?.search?.pageInfo?.endCursor ?: "")

            ResponseData.success(
                GitRepoData(pageInfo, repoList)
            )
        } catch (e: ApolloException) {
            ResponseData.error(e)
        }
    }

    override suspend fun getRepoById(id: String): Flow<ResponseData<GitRepo>> {
        return flow<ResponseData<GitRepo>> {
            dataBase.gitRepoDao().getById(id).apply {
                GitRepo(
                    this.repoId,
                    this.ownerName,
                    this.repoName,
                    this.createDate,
                    this.description,
                    this.forkCount,
                    this.starCount,
                    null,
                    this.repoUrl
                )
            }
        }.flowOn(Dispatchers.IO)
    }
}