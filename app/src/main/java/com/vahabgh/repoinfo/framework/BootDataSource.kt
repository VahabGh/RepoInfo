package com.vahabgh.repoinfo.framework

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.vahabgh.core.data.GitRepoDataSource
import com.vahabgh.core.data.ResponseData
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.core.domain.GitRepoData
import com.vahabgh.repoinfo.GetFirstListOfRepositoriesQuery
import com.vahabgh.repoinfo.presentation.db.GitRepoDatabase
import com.vahabgh.repoinfo.presentation.db.GitRepoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BootDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val firstItemsQuery: GetFirstListOfRepositoriesQuery,
    private val dataBase: GitRepoDatabase
) : GitRepoDataSource {


    override suspend fun getFirstItems(firstItemCount: Int): Flow<ResponseData<GitRepoData>> {
        return flow {
            try {
                val response = apolloClient.query(firstItemsQuery).await()
                val repoList = GitRepoDataMapper.mapRepoListFromServerFirst(response.data?.search?.edges)
                val pageInfo = GitRepoDataMapper.getPageInfoFirst(1, response.data?.search?.pageInfo)
                emit(ResponseData.success(
                        GitRepoData(pageInfo, repoList)))
            } catch (e: ApolloException) {
                emit(ResponseData.error(e))
            }
        }.flowOn(Dispatchers.IO)

    }


    override suspend fun saveRepos(pageIndex: Int, repos: List<GitRepo>): Flow<Boolean> {
        return flow {
            dataBase.gitRepoDao().insertAll(repos.map {
                GitRepoEntity(
                    it.id,
                    it.ownerName,
                    it.repoName,
                    it.createDate,
                    it.description,
                    it.forkCount.toInt(),
                    it.starCount.toInt(),
                    it.repoUrl,
                    pageIndex
                )
            })
            emit(true)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getRepos(pageIndex: Int): Flow<ResponseData<GitRepoData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRepoById(id: String): Flow<ResponseData<GitRepo>> {
        TODO("Not yet implemented")
    }
}