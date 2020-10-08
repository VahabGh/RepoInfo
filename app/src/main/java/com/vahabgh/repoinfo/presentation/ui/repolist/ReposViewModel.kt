package com.vahabgh.repoinfo.presentation.ui.repolist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.repoinfo.framework.RepoListInteractors
import com.vahabgh.repoinfo.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.util.*

class ReposViewModel @ViewModelInject constructor(private val interactors: RepoListInteractors) :
    BaseViewModel() {


    private val _repos = MutableLiveData<List<GitRepo>>()
    val repo: LiveData<List<GitRepo>>
        get() = _repos

    private val _pagination = MutableLiveData<Int>()
    val pagination: LiveData<Int>
        get() = _pagination

    private var allItems : MutableList<GitRepo> =  mutableListOf()

    fun getAllRepos(pageIndex: Int) {
        showProgress()
        viewModelScope.launch {
            interactors.getReposUseCase.invoke(pageIndex).single().fold({ data ->
                hideProgress()
                allItems.addAll(data)
                _repos.value = data
            }, {
                hideProgress()
            })
        }
    }

    companion object {
        const val IN_PAGINATION = 0
        const val PAGINATION_DONE = 1
        const val PAGINATION_ERROR = 2
    }

}