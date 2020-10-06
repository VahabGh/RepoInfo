package com.vahabgh.core.interactors

import com.vahabgh.core.data.GitRepoRepository
import com.vahabgh.core.domain.GitRepo

class SaveReposUseCase(private val gitRepoRepository: GitRepoRepository) {

    suspend fun invoke(pageIndex : Int,repos : List<GitRepo>) = gitRepoRepository.saveRepos(pageIndex,repos)

}