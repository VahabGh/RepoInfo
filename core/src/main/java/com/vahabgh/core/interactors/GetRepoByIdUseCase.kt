package com.vahabgh.core.interactors

import com.vahabgh.core.data.GitRepoRepository

class GetRepoByIdUseCase(private val gitRepoRepository: GitRepoRepository) {

    suspend fun invoke(id : String) = gitRepoRepository.getRepoById(id)

}