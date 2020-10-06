package com.vahabgh.core.interactors

import com.vahabgh.core.data.GitRepoRepository

class GetFirstReposUseCase(private val gitRepoRepository: GitRepoRepository) {

    suspend fun invoke(firstItemCount : Int) = gitRepoRepository.getFirstItems(firstItemCount)
}