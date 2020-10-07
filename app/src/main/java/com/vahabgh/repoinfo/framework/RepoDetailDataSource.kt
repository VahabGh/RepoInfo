//package com.vahabgh.repoinfo.framework
//
//import com.vahabgh.core.data.GitRepoDataSource
//import com.vahabgh.core.data.ResponseData
//import com.vahabgh.core.domain.GitRepo
//import com.vahabgh.core.domain.GitRepoData
//import com.vahabgh.core.domain.PageInfo
//import com.vahabgh.repoinfo.presentation.db.GitRepoDatabase
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.flowOn
//import javax.inject.Inject
//
//class RepoDetailDataSource @Inject constructor(
//    private val dataBase: GitRepoDatabase
//) : GitRepoDataSource {
//
//    override suspend fun getFirstItems(firstItemCount: Int): Flow<ResponseData<GitRepoData>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun saveRepos(pageIndex: Int, repos: List<GitRepo>): Flow<Boolean> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getRepos(pageIndex: Int): Flow<ResponseData<GitRepoData>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getRepoById(id: String): Flow<ResponseData<GitRepo>> {
//        return flow<ResponseData<GitRepo>> {
//            dataBase.gitRepoDao().getById(id).apply {
//                GitRepo(
//                    this.repoId,
//                    this.ownerName,
//                    this.repoName,
//                    this.createDate,
//                    this.description,
//                    this.forkCount,
//                    this.starCount,
//                    null,
//                    this.repoUrl
//                )
//            }
//        }.flowOn(Dispatchers.IO)
//    }
//
//}