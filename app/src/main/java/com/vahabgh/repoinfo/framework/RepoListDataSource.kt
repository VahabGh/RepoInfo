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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepoListDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val query: GetListOfRepoQuery,
    private val dataBase: GitRepoDatabase
) : GitRepoDataSource {

    override suspend fun getFirstItems(firstItemCount: Int): Flow<ResponseData<GitRepoData>> {
        TODO("Not yet implemented")
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
                    it.forkCount,
                    it.starCount,
                    it.repoUrl,
                    pageIndex
                )
            })
            emit(true)
        }.flowOn(Dispatchers.IO)
    }

//    override suspend fun getRepos(pageInfo: PageInfo): Flow<ResponseData<GitRepoData>> {
//        return flow {
//            val localData = dataBase.gitRepoDao().getAllByPageIndex(pageInfo.pageIndex)
//            if (localData.isNullOrEmpty())
//                emit(getDataFromServer(pageInfo))
//            else
//                emit(GitRepoDataMapper.mapDataFromDb(localData))
//        }.flowOn(Dispatchers.IO)
//    }

    override suspend fun getRepos(pageIndex: Int): Flow<ResponseData<GitRepoData>> {
        TODO("Not yet implemented")
    }

    private suspend fun getDataFromServer(pageInfo: PageInfo): ResponseData<GitRepoData> {
        return try {
            val newQuery = query.copy(start = pageInfo.start, end = pageInfo.end)
            val response = apolloClient.query(newQuery).await()
            val repoList = GitRepoDataMapper.mapRepoListFromServer(response.data?.search?.edges)
            val pageInfo = GitRepoDataMapper.getPageInfo(1, response.data?.search?.pageInfo)
            ResponseData.success(
                GitRepoData(pageInfo, repoList)
            )
        } catch (e: ApolloException) {
            ResponseData.error(e)
        }
    }

    override suspend fun getRepoById(id: String): Flow<ResponseData<GitRepo>> {
        TODO("Not yet implemented")
    }

}