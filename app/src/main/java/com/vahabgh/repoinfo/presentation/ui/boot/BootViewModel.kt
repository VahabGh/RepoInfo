package com.vahabgh.repoinfo.presentation.ui.boot

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.exception.ApolloException
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
        Log.i("BootResponse","started")
        viewModelScope.launch {
            bootInteractors.getRepos.invoke(50).single().fold({
                repos = it
                saveRepos(it)
            }, {
                hideProgress()
                _isBootDone.value = false
                handleExceptionMessage(it)
            })
        }
    }

    private fun handleExceptionMessage(exp : Throwable) {
        if (exp is ApolloException)
            setErrorMessage(exp.message)
    }


    private fun saveRepos(repos: List<GitRepo>) {
        viewModelScope.launch {
            repos.let {
                val result = bootInteractors.saveRepos.invoke(0,repos).single()
                if (result){
                    _isBootDone.postValue(true)
                }
                else {
                    hideProgress()
                    _isBootDone.postValue(false)
                }
            }
        }
    }

    fun retry() {
        if (repos == null)
            boot()
        else {
            showProgress()
            saveRepos(repos!!)
        }
    }

}