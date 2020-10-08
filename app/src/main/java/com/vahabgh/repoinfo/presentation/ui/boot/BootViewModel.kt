package com.vahabgh.repoinfo.presentation.ui.boot

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.repoinfo.framework.BootInteractors
import com.vahabgh.repoinfo.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class BootViewModel @ViewModelInject constructor(private val bootInteractors: BootInteractors) : BaseViewModel() {

    private val _isBootDone = MutableLiveData<Boolean>()
    val isBootDone: LiveData<Boolean>
        get() = _isBootDone

    private var repos: List<GitRepo>? = null

    fun boot() {
        showProgress()
        viewModelScope.launch {
            bootInteractors.getRepos.invoke(50).single().fold({
                repos = it
                saveRepos(it)
            }, {
                hideProgress()
                _isBootDone.value = false
            })
        }
    }

    private fun saveRepos(repos: List<GitRepo>) {
        viewModelScope.launch {
            repos.let {
                val result = bootInteractors.saveRepos.invoke(0,repos).single()
                if (result)
                    _isBootDone.value = true
                else {
                    hideProgress()
                    _isBootDone.value = false
                }
            }
        }
    }

    fun retry() {
        if (repos != null)
            boot()
        else {
            showProgress()
            saveRepos(repos!!)
        }
    }

}