package com.vahabgh.repoinfo.presentation.ui.repodetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.repoinfo.framework.RepoDetailInteractors
import com.vahabgh.repoinfo.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class DetailRepoViewModel @ViewModelInject constructor(private val interactors: RepoDetailInteractors) :
    BaseViewModel() {


    private val _repo = MutableLiveData<GitRepo>()
    val repo: LiveData<GitRepo>
        get() = _repo

    fun getRepoById(id: String) {
        if (id.isEmpty()){
            hideProgress()
            return
        }
        showProgress()
        viewModelScope.launch {
            interactors.getRepoById.invoke(id).single().fold({
                hideProgress()
                _repo.value = it
            }, {
                hideProgress()
            })
        }
    }

}