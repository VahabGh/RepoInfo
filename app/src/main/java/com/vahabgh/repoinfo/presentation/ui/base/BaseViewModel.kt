package com.vahabgh.repoinfo.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val _showLoading = MutableLiveData(true)
    val showLoading : LiveData<Boolean>
        get() = _showLoading

    private val _errorMessage = MutableLiveData("")
    val errorMessage : LiveData<String>
        get() = _errorMessage

    fun showProgress(){
        _showLoading.value = true
    }

    fun hideProgress(){
        _showLoading.postValue(false)
    }

    fun setErrorMessage(errorMessage : String){
        _errorMessage.value = errorMessage
    }
}
