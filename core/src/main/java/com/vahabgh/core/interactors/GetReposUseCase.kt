package com.vahabgh.core.interactors

import com.vahabgh.core.data.GitRepoRepository

class GetReposUseCase(private val gitRepoRepository: GitRepoRepository) {

    suspend fun invoke(pageIndex : Int) = gitRepoRepository.getRepos(pageIndex)
}