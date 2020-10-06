package com.vahabgh.repoinfo.framework

import com.vahabgh.core.interactors.GetFirstReposUseCase
import com.vahabgh.core.interactors.SaveReposUseCase
import javax.inject.Inject

data class BootInteractors @Inject constructor(
    val getRepos : GetFirstReposUseCase,
    val saveRepos : SaveReposUseCase
)