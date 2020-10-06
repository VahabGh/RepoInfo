package com.vahabgh.repoinfo.framework

import com.vahabgh.core.interactors.GetReposUseCase
import com.vahabgh.core.interactors.SaveReposUseCase

class RepoListInteractors (
    val getReposUseCase: GetReposUseCase,
    val saveReposUseCase: SaveReposUseCase
)